package com.macautoinc.widget.gateway.delegate;

import com.inductiveautomation.ignition.common.gson.Gson;
import com.inductiveautomation.ignition.common.gson.JsonObject;
import com.inductiveautomation.ignition.common.script.builtin.KeywordArgs;
import com.inductiveautomation.ignition.common.script.builtin.PyArgumentMap;
import com.inductiveautomation.perspective.gateway.api.Component;
import com.inductiveautomation.perspective.gateway.api.ComponentModelDelegate;
import com.inductiveautomation.perspective.gateway.api.ScriptCallable;
import com.inductiveautomation.perspective.gateway.messages.EventFiredMsg;
import org.python.core.Py;
import org.python.core.PyDictionary;
import org.python.core.PyObject;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The EChartModelDelegate class acts as a mediator between the Perspective frontend and the backend logic.
 * It defines methods that can be called from the frontend to manipulate EChart components, such as toggling series visibility,
 * showing or hiding series, adding annotations, and updating series data. It also handles events fired from the frontend,
 * processing them as needed.
 */
public class EChartModelDelegate extends ComponentModelDelegate {
    public static final String OUTBOUND_EVENT_NAME = "echart-response-event";
    public static final String INBOUND_EVENT_NAME = "echart-request-event";

    private AtomicBoolean toggleSeriesWaiting = new AtomicBoolean(false);
    private AtomicBoolean toggleSeriesReturn = new AtomicBoolean(false);

    public EChartModelDelegate(Component component) {
        super(component);
    }

    @Override
    protected void onStartup() {
        // Called when the Gateway's ComponentModel starts.  The start itself happens when the client project is
        // loading and includes an instance of the the component type in the page/view being started.
        log.debugf("Starting up delegate for '%s'!", component.getComponentAddressPath());
    }

    @Override
    protected void onShutdown() {
        // Called when the component is removed from the page/view and the model is shutting down.
        log.debugf("Shutting down delegate for '%s'!", component.getComponentAddressPath());
    }

    /**
     * Toggles the visibility of a series on the chart based on the series name.
     * It sends a request to the frontend to toggle the series and waits for a response to confirm the action.
     * This method uses a blocking wait to ensure the toggle action is completed before proceeding.
     *
     * @param pyArgs Python arguments passed from the script.
     * @param keywords Python keyword arguments.
     * @return true if the series was successfully toggled, false otherwise.
     * @throws Exception if the series name is null or no response is received from the frontend within a timeout period.
     */
    @ScriptCallable
    @KeywordArgs(names = {"seriesName"}, types = {String.class})
    public boolean toggleSeries(PyObject[] pyArgs, String[] keywords) throws Exception {
        PyArgumentMap argumentMap = PyArgumentMap.interpretPyArgs(pyArgs, keywords, EChartModelDelegate.class, "toggleSeries");
        String seriesName = argumentMap.getStringArg("seriesName");

        if (seriesName == null) {
            throw Py.ValueError("toggleSeries argument 'seriesName' cannot be None");
        }

        toggleSeriesWaiting.set(true);
        log.debugf("Calling toggleSeries with '%s'", seriesName);
        JsonObject payload = new JsonObject();
        payload.addProperty("functionToCall", "toggleSeries");
        payload.addProperty("seriesName", seriesName);
        fireEvent(OUTBOUND_EVENT_NAME, payload);

        int maxTryCount = 20;
        int tryCount = 0;
        while (toggleSeriesWaiting.get()) {
            tryCount += 1;
            if (tryCount >= maxTryCount) {
                toggleSeriesWaiting.set(false);
                throw new Exception("No message received from ApexChart, failing");
            }
            Thread.sleep(100);
        }

        toggleSeriesWaiting.set(false);
        return toggleSeriesReturn.get();
    }

    /**
     * Sends a request to the frontend to show a specific series on the chart.
     * The series to be shown is identified by its name.
     *
     * @param pyArgs Python arguments passed from the script.
     * @param keywords Python keyword arguments.
     * @throws Exception if the series name is null.
     */
    @ScriptCallable
    @KeywordArgs(names = {"seriesName"}, types = {String.class})
    public void showSeries(PyObject[] pyArgs, String[] keywords) throws Exception {
        PyArgumentMap argumentMap = PyArgumentMap.interpretPyArgs(pyArgs, keywords, EChartModelDelegate.class, "showSeries");
        String seriesName = argumentMap.getStringArg("seriesName");

        if (seriesName == null) {
            throw Py.ValueError("showSeries argument 'seriesName' cannot be None");
        }

        log.debugf("Calling showSeries with '%s'", seriesName);
        JsonObject payload = new JsonObject();
        payload.addProperty("functionToCall", "showSeries");
        payload.addProperty("seriesName", seriesName);
        fireEvent(OUTBOUND_EVENT_NAME, payload);
    }

    /**
     * Sends a request to the frontend to hide a specific series on the chart.
     * The series to be hidden is identified by its name.
     *
     * @param pyArgs Python arguments passed from the script.
     * @param keywords Python keyword arguments.
     * @throws Exception if the series name is null.
     */
    @ScriptCallable
    @KeywordArgs(names = {"seriesName"}, types = {String.class})
    public void hideSeries(PyObject[] pyArgs, String[] keywords) throws Exception {
        PyArgumentMap argumentMap = PyArgumentMap.interpretPyArgs(pyArgs, keywords, EChartModelDelegate.class, "hideSeries");
        String seriesName = argumentMap.getStringArg("seriesName");

        if (seriesName == null) {
            throw Py.ValueError("hideSeries argument 'seriesName' cannot be None");
        }

        log.debugf("Calling hideSeries with '%s'", seriesName);
        JsonObject payload = new JsonObject();
        payload.addProperty("functionToCall", "hideSeries");
        payload.addProperty("seriesName", seriesName);
        fireEvent(OUTBOUND_EVENT_NAME, payload);
    }

    /**
     * Adds a point annotation to the chart. The annotation details are specified in the options dictionary.
     * This method also allows specifying whether the annotation should be stored in memory.
     *
     * @param pyArgs Python arguments passed from the script.
     * @param keywords Python keyword arguments.
     * @throws Exception if the options dictionary is null.
     */
    @ScriptCallable
    @KeywordArgs(names = {"options", "pushToMemory"}, types = {PyDictionary.class, Boolean.class})
    public void addPointAnnotation(PyObject[] pyArgs, String[] keywords) throws Exception {
        PyArgumentMap argumentMap = PyArgumentMap.interpretPyArgs(pyArgs, keywords, EChartModelDelegate.class, "addPointAnnotation");
        PyDictionary options = (PyDictionary) argumentMap.get("options");
        Boolean pushToMemory = argumentMap.getBooleanArg("pushToMemory", true);

        Gson gson = new Gson();
        log.debug("Calling addPointAnnotation");
        JsonObject payload = new JsonObject();
        payload.addProperty("functionToCall", "addPointAnnotation");
        payload.add("options", gson.toJsonTree(options));
        payload.addProperty("pushToMemory", pushToMemory);
        fireEvent(OUTBOUND_EVENT_NAME, payload);
    }

    /**
     * Clears all annotations from the chart by sending a request to the frontend.
     *
     * @throws Exception if an error occurs during the operation.
     */
    @ScriptCallable
    public void clearAnnotations() throws Exception {
        log.debug("Calling clearAnnotations");
        JsonObject payload = new JsonObject();
        payload.addProperty("functionToCall", "clearAnnotations");
        fireEvent(OUTBOUND_EVENT_NAME, payload);
    }

    /**
     * Updates the series data on the chart. This method allows updating the series with new data and optionally
     * animating the update process.
     *
     * @param pyArgs Python arguments passed from the script.
     * @param keywords Python keyword arguments.
     * @throws Exception if an error occurs during the operation.
     */
    @ScriptCallable
    @KeywordArgs(names = {"newSeries", "animate"}, types = {List.class, Boolean.class})
    public void updateSeries(PyObject[] pyArgs, String[] keywords) throws Exception {
        PyArgumentMap argumentMap = PyArgumentMap.interpretPyArgs(pyArgs, keywords, EChartModelDelegate.class, "updateSeries");
        List newSeries = (List) argumentMap.get("newSeries");
        Boolean animate = argumentMap.getBooleanArg("animate", true);

        Gson gson = new Gson();
        log.debug("Calling updateSeries");
        JsonObject payload = new JsonObject();
        payload.addProperty("functionToCall", "updateSeries");
        payload.add("newSeries", gson.toJsonTree(newSeries));
        payload.addProperty("animate", animate);
        fireEvent(OUTBOUND_EVENT_NAME, payload);
    }

    /**
     * Handles events fired from the client side. This method is invoked when a ComponentStoreDelegate event is received.
     * It processes the event, specifically looking for the result of toggling a series visibility, and updates the
     * internal state accordingly.  When a ComponentStoreDelegate event is fired from the client side, it comes through
     * this method.
     *
     * @param message The event message received from the client.
     */
    @Override
    public void handleEvent(EventFiredMsg message) {
        log.debugf("Received EventFiredMessage of type: %s", message.getEventName());

        if (message.getEventName().equals(INBOUND_EVENT_NAME)) {
            JsonObject payload = message.getEvent();
            toggleSeriesReturn.set(payload.get("result").getAsBoolean());
            toggleSeriesWaiting.set(false);
        }
    }
}
