package com.simple.action;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.simple.annotations.ActionController;
import com.simple.annotations.RequestUrl;
import com.simple.html.template.PebbleTemplateUtil;
import com.simple.http.Request;
import com.simple.http.Response;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangjie on 16/7/30.
 */
@ActionController
public class BaseAction {



    @RequestUrl(url = "/base.html")
    public String login(Request request, Response response) throws IOException {


        PebbleTemplate compiledTemplate = null;
        try {
            compiledTemplate = PebbleTemplateUtil.engine.getTemplate("web/html/base.html");
            Map<String, Object> context = new HashMap<String,Object>();
            context.put("name", "Mitchell");
            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, context);
            String output = writer.toString();
            response.write(output);
        } catch (PebbleException e) {
            response.write("资源不存在!");
            //e.printStackTrace();
        } catch (IOException e) {
            response.write("资源不存在!");
            //e.printStackTrace();
        }


        return null;
    }

}
