package demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.script.Invocable;
import javax.script.ScriptEngine;

@SpringBootApplication
@Controller
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    ScriptEngine scriptEngine() {
        return new JavaScriptEngine()
                .polyfill()
                .load("static/bundle.js")
                .get();
    }

    // JSONマッパー
    @Autowired
    ObjectMapper objectMapper;

    // ScriptEngineのラッパー
    @Autowired
    ScriptEngine engine;

    @RequestMapping("/")
    String index(Model model) throws Exception {
        Greet initialData = new Greet(100, "Data on Server");
        Object markup = ((Invocable) engine)
                .invokeFunction("renderOnServer", initialData);
        model.addAttribute("markup", markup);
        model.addAttribute("initialData",
                objectMapper.writeValueAsString(initialData));
        return "index";
    }

    public static class Greet {

        private int id;
        private String content;

        public Greet() {
        }

        public Greet(int id, String content) {
            this.id = id;
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}