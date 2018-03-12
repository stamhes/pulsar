/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.warps.pulsar.normalizer;

import org.apache.hadoop.conf.Configured;
import org.apache.oro.text.regex.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.warps.pulsar.common.URLUtil;
import org.warps.pulsar.common.config.ImmutableConfig;
import org.warps.pulsar.crawl.filter.UrlNormalizer;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Converts URLs to a normal form:
 * <ul>
 * <li>remove dot segments in path: <code>/./</code> or <code>/../</code></li>
 * <li>remove default ports, e.g. 80 for protocol <code>http://</code></li>
 * </ul>
 */
public class BasicUrlNormalizer extends Configured implements UrlNormalizer {
    public static final Logger LOG = LoggerFactory.getLogger(BasicUrlNormalizer.class);
    private final Rule relativePathRule;
    private final Rule leadingRelativePathRule;
    private final Rule currentPathRule;
    private final Rule adjacentSlashRule;
    private Perl5Compiler compiler = new Perl5Compiler();
    private ThreadLocal<Perl5Matcher> matchers = new ThreadLocal<Perl5Matcher>() {
        protected Perl5Matcher initialValue() {
            return new Perl5Matcher();
        }
    };

    public BasicUrlNormalizer(ImmutableConfig conf) {
        try {
            // this pattern tries to find spots like "/xx/../" in the url, which
            // could be replaced by "/" xx consists of chars, different then "/"
            // (slash) and needs to have at least one char different from "."
            relativePathRule = new Rule();
            relativePathRule.pattern = (Perl5Pattern) compiler.compile(
                    "(/[^/]*[^/.]{1}[^/]*/\\.\\./)", Perl5Compiler.READ_ONLY_MASK);
            relativePathRule.substitution = new Perl5Substitution("/");

            // this pattern tries to find spots like leading "/../" in the url,
            // which could be replaced by "/"
            leadingRelativePathRule = new Rule();
            leadingRelativePathRule.pattern = (Perl5Pattern) compiler.compile(
                    "^(/\\.\\./)+", Perl5Compiler.READ_ONLY_MASK);
            leadingRelativePathRule.substitution = new Perl5Substitution("/");

            // this pattern tries to find spots like "/./" in the url,
            // which could be replaced by "/"
            currentPathRule = new Rule();
            currentPathRule.pattern = (Perl5Pattern) compiler.compile("(/\\./)", Perl5Compiler.READ_ONLY_MASK);
            currentPathRule.substitution = new Perl5Substitution("/");

            // this pattern tries to find spots like "xx//yy" in the url,
            // which could be replaced by a "/"
            adjacentSlashRule = new Rule();
            adjacentSlashRule.pattern = (Perl5Pattern) compiler.compile("/{2,}", Perl5Compiler.READ_ONLY_MASK);
            adjacentSlashRule.substitution = new Perl5Substitution("/");
        } catch (MalformedPatternException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String normalize(String urlString, String scope) {
        URL url = URLUtil.getURLOrNull(urlString);
        if (url == null) {
            return null;
        }

        String protocol = url.getProtocol();
        String host = url.getHost();
        int port = url.getPort();
        String file = url.getFile();

        boolean changed = false;

        if (!urlString.startsWith(protocol)) // protocol was lowercased
            changed = true;

        if ("http".equals(protocol) || "https".equals(protocol)
                || "ftp".equals(protocol)) {

            if (host != null) {
                String newHost = host.toLowerCase(); // lowercase host
                if (!host.equals(newHost)) {
                    host = newHost;
                    changed = true;
                }
            }

            if (port == url.getDefaultPort()) { // uses default port
                port = -1; // so don't specify it
                changed = true;
            }

            if (file == null || "".equals(file)) { // add a slash
                file = "/";
                changed = true;
            }

            if (url.getRef() != null) { // remove the ref
                changed = true;
            }

            // check for unnecessary use of "/../"
            String file2 = substituteUnnecessaryRelativePaths(file);

            if (!file.equals(file2)) {
                changed = true;
                file = file2;
            }

        }

        if (changed) {
            try {
                urlString = new URL(protocol, host, port, file).toString();
            } catch (MalformedURLException e) {
                urlString = null;
                LOG.warn(e.getMessage());
            }
        }

        return urlString;
    }

    private String substituteUnnecessaryRelativePaths(String file) {
        String fileWorkCopy = file;
        int oldLen = file.length();
        int newLen = oldLen - 1;

        // All substitutions will be done step by step, to ensure that certain
        // constellations will be normalized, too
        //
        // For example: "/aa/bb/../../cc/../foo.html will be normalized in the
        // following manner:
        // "/aa/bb/../../cc/../foo.html"
        // "/aa/../cc/../foo.html"
        // "/cc/../foo.html"
        // "/foo.html"
        //
        // The normalization also takes care of leading "/../", which will be
        // replaced by "/", because this is a rather a sign of bad webserver
        // configuration than of a wanted link. For example, urls like
        // "http://www.foo.com/../" should return a http 404 error instead of
        // redirecting to "http://www.foo.com".
        //
        Perl5Matcher matcher = matchers.get();

        while (oldLen != newLen) {
            // substitue first occurence of "/xx/../" by "/"
            oldLen = fileWorkCopy.length();
            fileWorkCopy = Util.substitute(matcher, relativePathRule.pattern,
                    relativePathRule.substitution, fileWorkCopy, 1);

            // remove leading "/../"
            fileWorkCopy = Util.substitute(matcher, leadingRelativePathRule.pattern,
                    leadingRelativePathRule.substitution, fileWorkCopy, 1);

            // remove unnecessary "/./"
            fileWorkCopy = Util.substitute(matcher, currentPathRule.pattern,
                    currentPathRule.substitution, fileWorkCopy, 1);

            // collapse adjacent slashes with "/"
            fileWorkCopy = Util.substitute(matcher, adjacentSlashRule.pattern,
                    adjacentSlashRule.substitution, fileWorkCopy, 1);

            newLen = fileWorkCopy.length();
        }

        return fileWorkCopy;
    }

    /**
     * Class which holds a compiled pattern and its corresponding substition
     * string.
     */
    private static class Rule {
        public Perl5Pattern pattern;
        public Perl5Substitution substitution;
    }
}
