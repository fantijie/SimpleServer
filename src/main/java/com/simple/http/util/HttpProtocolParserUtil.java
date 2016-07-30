package com.simple.http.util;

import com.simple.http.Request;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Created by liangjie on 16/7/30.
 */
public class HttpProtocolParserUtil {

    public static Charset charset = Charset.forName("UTF-8");

    public   Request parser(ByteBuffer buffer) {

        Request request = new Request();

        buffer.flip();


        String reqStr = charset.decode(buffer).toString();
        String[] split = reqStr.split("\r\n");
        try {


            for (int i = 0; i < split.length; i++) {
                if (i == 0) {
                    String[] split1 = split[0].split(" ");
                    request.setMethod(split1[0]);
                    request.setRequestURL(split1[1]);
                    request.setProtocol(split1[2]);
                }else{

                    if (split[i].startsWith("Cookie")) {
                        String cookies = split[i].substring(7, split[i].length()).trim();

                        String[] split1 = cookies.split(";");

                        for (String s : split1) {

                            String[] split2 = s.split("=");
                            if (split2[0].equals("SessionID")) {

                                request.setSessioinId(split2[1]);
                            }

                        }


                    }


                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            request.setRequestURL("base.html");
        }

        return request;
    }

}
