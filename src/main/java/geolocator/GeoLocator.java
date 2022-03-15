package geolocator;

import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;

/**
 * Interface for obtaining geolocation information of an IP address. The actual
 * implementation uses the <a href="http://ip-api.com/">IP-API</a> service. The
 * {@code static} interface method {@link #newInstance()} is provided to obtain
 * a {@code GeoLocator} object.
 */
public interface GeoLocator {

    /**
     * Returns geolocation information about the JVM running the application.
     *
     * @return an object wrapping the geolocation information returned
     * @throws feign.FeignException if any error occurs
     */
    @RequestLine("GET")
    GeoLocation getGeoLocation();

    /**
     *  Returns geolocation information about the IP address or host name specified.
     *
     * @param ipOrHostName the IP address or host name
     * @return an object wrapping the geolocation information returned
     * @throws feign.FeignException if any error occurs
     */
    @RequestLine("GET /{ipOrHostName}")
    GeoLocation getGeoLocation(@Param("ipOrHostName") String ipOrHostName);

    /**
     * Creates and returns a new object that implements the interface.
     *
     * @return a new object that implements the interface
     */
    static GeoLocator newInstance() {
        return Feign.builder()
            .decoder(new JacksonDecoder())
            .target(GeoLocator.class, "http://ip-api.com/json/");
    }

}
