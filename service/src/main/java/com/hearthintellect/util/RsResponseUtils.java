package com.hearthintellect.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Util class for generating {@link Response}s.
 */
public abstract class RsResponseUtils {
    private static final Logger LOG = LoggerFactory.getLogger(RsResponseUtils.class);

    private static final String DOC_URL = ""; // TODO to be set

    private static final String NOT_FOUND = "Not Found";

    /**
     * Returns a {@link JSONObject} as follows:
     *
     * <pre>
     * {
     *     status_code: ${statusCode},
     *     message: ${message},
     *     documentation_url: ${DOC_URL}
     * }
     * </pre>
     *
     * @param statusCode variable {@code statusCode} for the resulting {@code JSONObject}.
     * @param message variable {@code message} for the resulting {@code JSONObject}.
     * @return the resulting {@code JSONObject}.
     */
    public static JSONObject message(int statusCode, String message) {
        return new JSONObject().put("status_code", statusCode)
                   .put("message", message)
                   .put("documentation_url", DOC_URL);
    }

    /**
     * Returns a {@code 404 Response} with JSON object as follows:
     * <pre>
     *     {
     *         status_code: 404,
     *         message: "Not Found",
     *         documentation_url: ${DOC_URL}
     *     }
     * </pre>
     *
     * @return the resulting {@code 404 Response}.
     *
     * @see #notFound(String)
     */
    public static Response notFound() {
        return notFound(NOT_FOUND);
    }

    /**
     * Returns a {@code Response} with the given status code and a JSON object as follows:
     * <pre>
     *     {
     *         status_code: ${statusCode},
     *         message: ${message},
     *         documentation_url: ${DOC_URL}
     *     }
     * </pre>
     *
     * @param statusCode variable {@code statusCode} for the resulting {@code Response}.
     * @param message variable {@code message} for the resulting {@code Response}.
     * @return the resulting {@code Response}.
     */
    public static Response response(int statusCode, String message) {
        return Response.status(statusCode).entity(message(statusCode, message).toString(4)).build();
    }

    /**
     * Returns a {@code 404 Response} with JSON object as follows:
     *
     * <pre>
     *     {
     *         message: ${message},
     *         documentation_url: ${DOC_URL}
     *     }
     * </pre>
     *
     * @param message variable {@code message} for the resulting {@code Response}.
     *
     * @return the resulting {@code 404 Response}.
     */
    public static Response notFound(String message) {
        return response(404, message);
    }

    /**
     * Returns a {@code 400 Response} with JSON object as follows:
     * <pre>
     *     {
     *         message: ${message},
     *         documentation_url: ${DOC_URL}
     *     }
     * </pre>
     *
     * @param message variable {@code message} for the resulting {@code Response}.
     *
     * @return the resulting {@code 400 Response}.
     */
    public static Response badRequest(String message) {
        return response(400, message);
    }

    /**
     * Returns a {@code 403 Response} with JSON object as follows:
     * <pre>
     *     {
     *         message: ${message},
     *         documentation_url: ${DOC_URL}
     *     }
     * </pre>
     *
     * @param message variable {@code message} for the resulting {@code Response}.
     *
     * @return the resulting {@code 403 Response}.
     */
    public static Response forbidden(String message) {
        return response(403, message);
    }

    /**
     * Returns a {@code 401 Response} with JSON object as follows:
     * <pre>
     *     {
     *         message: ${message},
     *         documentation_url: ${DOC_URL}
     *     }
     * </pre>
     *
     * @param message variable {@code message} for the resulting {@code Response}.
     *
     * @return the resulting {@code 401 Response}.
     */
    public static Response unauthorized(String message) {
        return response(401, message);
    }

    /**
     * Returns a {@code 201 Response} with the given location in URL setting in the {@code entity_url}
     * header field and the given message as its body content.
     * <p>
     * If the method failed to parse the given location as a valid URL, the given URL will <b>not</b>
     * be contained in the headers.
     *
     * @param location URL for the created entity.
     * @return a {@code 201 Response}.
     */
    public static Response created(String location, String message) {
        URI uri;

        try {
            uri = new URI(location);
        } catch (URISyntaxException e) {
            LOG.error("Failed to parse string `" + location + "` as a URI, returning empty");
            return Response.status(201).entity(message(201, message).toString(4)).build();
        }

        return Response.created(uri).entity(message(201, message).put("entity_url", uri.toString()).toString(4)).build();
    }

    /**
     * Returns an empty {@code 200 Response}.
     */
    public static Response ok() {
        return Response.ok().build();
    }

    /**
     * Returns a {@code 200 Response} with the given message as its body content.
     *
     * @param message the given message.
     * @return a {@code 200 Response} with the given message.
     */
    public static Response ok(String message) {
        return ok(message(200, message));
    }

    /**
     * Returns a {@code 200 Response} with the given {@link JSONObject} as its body content.
     *
     * @param json the given {@code JSONObject}.
     * @return a {@code 200 Response} with the given {@code JSONObject}.
     */
    public static Response ok(JSONObject json) {
        return Response.ok().entity(json.toString(4)).build();
    }

    /**
     * Returns a {@code 200 Response} with the given {@link JSONArray} as its body content.
     *
     * @param json the given {@code JSONArray}.
     * @return a {@code 200 Response} with the given {@code JSONArray}.
     */
    public static Response ok(JSONArray json) {
        return Response.ok().entity(json.toString(4)).build();
    }
}
