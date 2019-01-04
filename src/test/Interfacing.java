package test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Interfacing {

    public static void main(String[] args) {
        try {
            ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
            scriptEngine.eval("print('Executing JavaScript code');");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
