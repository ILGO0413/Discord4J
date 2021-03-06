/*
 * This file is part of Discord4J.
 *
 * Discord4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Discord4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Discord4J. If not, see <http://www.gnu.org/licenses/>.
 */

package discord4j.rest.request;

import io.netty.handler.codec.http.HttpHeaders;
import reactor.netty.http.client.HttpClientResponse;

import java.time.Duration;

public class ResponseHeaderStrategy implements RateLimitStrategy {

    @Override
    public Duration apply(HttpClientResponse response) {
        HttpHeaders headers = response.responseHeaders();
        int remaining = headers.getInt("X-RateLimit-Remaining", -1);
        if (remaining == 0) {
            long resetAt = (long) (Double.parseDouble(headers.get("X-RateLimit-Reset-After")) * 1000);
            return Duration.ofMillis(resetAt);
        }
        return Duration.ZERO;
    }
}
