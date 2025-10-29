package com.pigx.engine.oauth2.authorization.matcher;

import com.pigx.engine.core.autoconfigure.oauth2.domain.HerodotusRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.RequestPath;
import org.springframework.lang.Nullable;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.util.Assert;
import org.springframework.web.util.ServletRequestPathUtils;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Objects;


public class HerodotusPathPatternRequestMatcher implements HerodotusRequestMatcher {

    private final PathPattern pattern;

    private final HerodotusRequestMatcher method;

    /**
     * Creates a {@link HerodotusPathPatternRequestMatcher} that uses the provided {@code pattern}.
     * <p>
     * The {@code pattern} should be relative to the context path
     * </p>
     *
     * @param pattern the pattern used to match
     */
    private HerodotusPathPatternRequestMatcher(PathPattern pattern, HerodotusRequestMatcher method) {
        this.pattern = pattern;
        this.method = method;
    }

    /**
     * Use {@link PathPatternParser#defaultInstance} to parse path patterns.
     *
     * @return a {@link Builder} that treats URIs as relative to the context path, if any
     */
    public static Builder withDefaults() {
        return new Builder();
    }

    /**
     * Use this {@link PathPatternParser} to parse path patterns.
     *
     * @param parser the {@link PathPatternParser} to use
     * @return a {@link Builder} that treats URIs as relative to the given
     * {@code servletPath}
     */
    public static Builder withPathPatternParser(PathPatternParser parser) {
        Assert.notNull(parser, "pathPatternParser cannot be null");
        return new Builder(parser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean matches(HttpServletRequest request) {
        return matcher(request).isMatch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchResult matcher(HttpServletRequest request) {
        if (!this.method.matches(request)) {
            return MatchResult.notMatch();
        }
        PathContainer path = getPathContainer(request);
        PathPattern.PathMatchInfo info = this.pattern.matchAndExtract(path);
        return (info != null) ? MatchResult.match(info.getUriVariables()) : MatchResult.notMatch();
    }

    @Override
    public boolean matches(HerodotusRequest request) {
        return matcher(request).isMatch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchResult matcher(HerodotusRequest request) {
        if (!this.method.matches(request)) {
            return MatchResult.notMatch();
        }

        PathContainer path = getPathContainer(request.getPattern());
        PathPattern.PathMatchInfo info = this.pattern.matchAndExtract(path);
        return (info != null) ? MatchResult.match(info.getUriVariables()) : MatchResult.notMatch();
    }

    private PathContainer getPathContainer(HttpServletRequest request) {
        RequestPath path;
        if (ServletRequestPathUtils.hasParsedRequestPath(request)) {
            path = ServletRequestPathUtils.getParsedRequestPath(request);
        } else {
            path = ServletRequestPathUtils.parseAndCache(request);
            ServletRequestPathUtils.clearParsedRequestPath(request);
        }
        PathContainer contextPath = path.contextPath();
        return path.subPath(contextPath.elements().size());
    }

    private PathContainer getPathContainer(String uri) {
        return RequestPath.parse(uri, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof HerodotusPathPatternRequestMatcher that)) {
            return false;
        }
        return Objects.equals(this.pattern, that.pattern) && Objects.equals(this.method, that.method);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.pattern, this.method);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder request = new StringBuilder();
        if (this.method instanceof HttpMethodRequestMatcher m) {
            request.append(m.method.name()).append(' ');
        }
        return "PathPattern [" + request + this.pattern + "]";
    }

    /**
     * A builder for specifying various elements of a request for the purpose of creating
     * a {@link HerodotusPathPatternRequestMatcher}.
     *
     * <p>
     * To match a request URI like {@code /app/servlet/my/resource/**} where {@code /app}
     * is the context path, you can do
     * {@code PathPatternRequestMatcher.withDefaults().matcher("/servlet/my/resource/**")}
     *
     * <p>
     * If you have many paths that have a common path prefix, you can use
     * {@link #basePath} to reduce repetition like so:
     *
     * <pre>
     *     PathPatternRequestMatcher.Builder mvc = withDefaults().basePath("/mvc");
     *     http
     *         .authorizeHttpRequests((authorize) -> authorize
     *              .requestMatchers(mvc.matcher("/user/**")).hasAuthority("user")
     *              .requestMatchers(mvc.matcher("/admin/**")).hasAuthority("admin")
     *         )
     *             ...
     * </pre>
     */
    public static final class Builder {

        private final PathPatternParser parser;

        private final String basePath;

        Builder() {
            this(PathPatternParser.defaultInstance);
        }

        Builder(PathPatternParser parser) {
            this(parser, "");
        }

        Builder(PathPatternParser parser, String basePath) {
            this.parser = parser;
            this.basePath = basePath;
        }

        /**
         * Match requests starting with this {@code basePath}.
         *
         * <p>
         * Prefixes should be of the form {@code /my/prefix}, starting with a slash, not
         * ending in a slash, and not containing and wildcards
         *
         * @param basePath the path prefix
         * @return the {@link Builder} for more configuration
         */
        public Builder basePath(String basePath) {
            Assert.notNull(basePath, "basePath cannot be null");
            Assert.isTrue(basePath.startsWith("/"), "basePath must start with '/'");
            Assert.isTrue(!basePath.endsWith("/"), "basePath must not end with a slash");
            Assert.isTrue(!basePath.contains("*"), "basePath must not contain a star");
            return new Builder(this.parser, basePath);
        }

        /**
         * Match requests having this path pattern.
         *
         * <p>
         * When the HTTP {@code method} is null, then the matcher does not consider the
         * HTTP method
         *
         * <p>
         * Path patterns always start with a slash and may contain placeholders. They can
         * also be followed by {@code /**} to signify all URIs under a given path.
         *
         * <p>
         * These must be specified relative to any context path prefix. A
         * {@link #basePath} may be specified to reuse a common prefix, for example a
         * servlet path.
         *
         * <p>
         * The following are valid patterns and their meaning
         * <ul>
         * <li>{@code /path} - match exactly and only `/path`</li>
         * <li>{@code /path/**} - match `/path` and any of its descendents</li>
         * <li>{@code /path/{value}/**} - match `/path/subdirectory` and any of its
         * descendents, capturing the value of the subdirectory in
         * {@link RequestAuthorizationContext#getVariables()}</li>
         * </ul>
         *
         * <p>
         * A more comprehensive list can be found at {@link PathPattern}.
         *
         * @param path the path pattern to match
         * @return the {@link Builder} for more configuration
         */
        public HerodotusPathPatternRequestMatcher matcher(String path) {
            return matcher(null, path);
        }

        /**
         * Match requests having this {@link HttpMethod} and path pattern.
         *
         * <p>
         * When the HTTP {@code method} is null, then the matcher does not consider the
         * HTTP method
         *
         * <p>
         * Path patterns always start with a slash and may contain placeholders. They can
         * also be followed by {@code /**} to signify all URIs under a given path.
         *
         * <p>
         * These must be specified relative to any context path prefix. A
         * {@link #basePath} may be specified to reuse a common prefix, for example a
         * servlet path.
         *
         * <p>
         * The following are valid patterns and their meaning
         * <ul>
         * <li>{@code /path} - match exactly and only `/path`</li>
         * <li>{@code /path/**} - match `/path` and any of its descendents</li>
         * <li>{@code /path/{value}/**} - match `/path/subdirectory` and any of its
         * descendents, capturing the value of the subdirectory in
         * {@link RequestAuthorizationContext#getVariables()}</li>
         * </ul>
         *
         * <p>
         * A more comprehensive list can be found at {@link PathPattern}.
         *
         * @param method the {@link HttpMethod} to match, may be null
         * @param path   the path pattern to match
         * @return the {@link Builder} for more configuration
         */
        public HerodotusPathPatternRequestMatcher matcher(@Nullable HttpMethod method, String path) {
            Assert.notNull(path, "pattern cannot be null");
            Assert.isTrue(path.startsWith("/"), "pattern must start with a /");
            PathPattern pathPattern = this.parser.parse(this.basePath + path);
            return new HerodotusPathPatternRequestMatcher(pathPattern,
                    (method != null) ? new HttpMethodRequestMatcher(method) : HerodotusAnyRequestMatcher.INSTANCE);
        }

        public HerodotusPathPatternRequestMatcher matcher(HerodotusRequest request) {
            HttpMethod method = null;
            if (StringUtils.isNotBlank(request.getHttpMethod())) {
                method = HttpMethod.valueOf(request.getHttpMethod());
            }
            return matcher(method, request.getPattern());
        }

    }

    private static final class HttpMethodRequestMatcher implements HerodotusRequestMatcher {

        private final HttpMethod method;

        HttpMethodRequestMatcher(HttpMethod method) {
            this.method = method;
        }

        @Override
        public boolean matches(HttpServletRequest request) {
            return this.method.name().equals(request.getMethod());
        }

        @Override
        public boolean matches(HerodotusRequest request) {
            return Strings.CI.equals(this.method.name(), request.getHttpMethod());
        }

        @Override
        public String toString() {
            return "HttpMethod [" + this.method + "]";
        }

    }
}
