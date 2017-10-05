import channel.ChannelResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws UnirestException {
        initMapper();
        ChannelResponse channelResponse = get();

        System.out.println(channelResponse.items.size());
    }

    private static String getObject() throws UnirestException {
        HttpResponse<String> response = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
                .queryString("key", "AIzaSyCPALiCXC-2GmLoJySc-AdyxW63vpSki6E")
                .queryString("part", "snippet,contentDetails,statistics")
                .queryString("id", "UC_x5XG1OV2P6uZZ5FSM9Ttw")
                .asString();

        return response.getBody();
    }

    private static ChannelResponse get() throws UnirestException {
        HttpResponse<ChannelResponse> response = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
                .queryString("key", "AIzaSyCPALiCXC-2GmLoJySc-AdyxW63vpSki6E")
                .queryString("part", "snippet,contentDetails,statistics")
                .queryString("id", "UC_x5XG1OV2P6uZZ5FSM9Ttw")
                .asObject(ChannelResponse.class);

        return response.getBody();
    }


    private static void initMapper(){
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


}
