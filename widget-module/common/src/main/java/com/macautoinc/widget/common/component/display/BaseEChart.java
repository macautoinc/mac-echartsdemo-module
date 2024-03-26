package com.macautoinc.widget.common.component.display;

import com.inductiveautomation.ignition.common.gson.JsonParser;
import com.inductiveautomation.ignition.common.jsonschema.JsonSchema;
import com.inductiveautomation.perspective.common.api.ComponentDescriptor;
import com.inductiveautomation.perspective.common.api.ComponentDescriptorImpl;
import com.macautoinc.widget.common.Components;

import javax.swing.*;
import java.io.InputStreamReader;
import java.util.Objects;


/**
 * Describes the component to the Java registry so the gateway and designer know to look for the front end elements.
 * In a 'common' scope so that it's referencable by both gateway and designer.
 */
public class BaseEChart {

    // unique ID of the component which perfectly matches that provided in the javascript's ComponentMeta implementation
    public static String COMPONENT_ID = "macautoinc.display.echart";

    /**
     * The schema provided with the component descriptor. Use a schema instead of a plain JsonObject because it gives
     * us a little more type information, allowing the designer to highlight mismatches where it can detect them.
     */
    public static JsonSchema SCHEMA =
            JsonSchema.parse(Objects.requireNonNull(Components.class.getResourceAsStream("/echart.props.json")));

//    public static ComponentEventDescriptor ANIMATION_END_HANDLER = new ComponentEventDescriptor("animationEndHandler", "Fires when the chart’s initial animation is finished", JsonSchema.parse(Components.class.getResourceAsStream("/apexcharts.event.empty.props.json")));
//    public static ComponentEventDescriptor BEFORE_MOUNT_HANDLER = new ComponentEventDescriptor("beforeMountHandler", "Fires before the chart has been drawn on screen", JsonSchema.parse(Components.class.getResourceAsStream("/apexcharts.event.empty.props.json")));
//    public static ComponentEventDescriptor MOUNTED_HANDLER = new ComponentEventDescriptor("mountedHandler", "Fires after the chart has been drawn on screen", JsonSchema.parse(Components.class.getResourceAsStream("/apexcharts.event.empty.props.json")));
//    public static ComponentEventDescriptor UPDATED_HANDLER = new ComponentEventDescriptor("updatedHandler", "Fires when the chart has been dynamically updated either with updateOptions() or updateSeries() functions", JsonSchema.parse(Components.class.getResourceAsStream("/apexcharts.event.empty.props.json")));
//    public static ComponentEventDescriptor CLICK_HANDLER = new ComponentEventDescriptor("clickHandler", "Fires when user clicks on any area of the chart.", JsonSchema.parse(Components.class.getResourceAsStream("/apexcharts.event.mouse.props.json")));
//    public static ComponentEventDescriptor MOUSE_MOVE_HANDLER = new ComponentEventDescriptor("mouseMoveHandler", "Fires when user moves mouse on any area of the chart.", JsonSchema.parse(Components.class.getResourceAsStream("/apexcharts.event.mouse.props.json")));
//    public static ComponentEventDescriptor LEGEND_CLICK_HANDLER = new ComponentEventDescriptor("legendClickHandler", "Fires when user clicks on legend.", JsonSchema.parse(Components.class.getResourceAsStream("/apexcharts.event.seriesindex.props.json")));
//    public static ComponentEventDescriptor MARKER_CLICK_HANDLER = new ComponentEventDescriptor("markerClickHandler", "Fires when user clicks on the markers.", JsonSchema.parse(Components.class.getResourceAsStream("/apexcharts.event.mouse.props.json")));
//    public static ComponentEventDescriptor SELECTION_HANDLER = new ComponentEventDescriptor("selectionHandler", "Fires when user selects rect using the selection tool.", JsonSchema.parse(Components.class.getResourceAsStream("/apexcharts.event.xaxis.props.json")));
//    public static ComponentEventDescriptor DATA_POINT_SELECTION_HANDLER = new ComponentEventDescriptor("dataPointSelectionHandler", "Fires when user clicks on a datapoint (bar/column/marker/bubble/donut-slice).", JsonSchema.parse(Components.class.getResourceAsStream("/apexcharts.event.mouse.props.json")));
//    public static ComponentEventDescriptor DATA_POINT_MOUSE_ENTER_HANDLER = new ComponentEventDescriptor("dataPointMouseEnterHandler", "Fires when user’s mouse enter on a datapoint (bar/column/marker/bubble/donut-slice).", JsonSchema.parse(Components.class.getResourceAsStream("/apexcharts.event.mouse.props.json")));
//    public static ComponentEventDescriptor DATA_POINT_MOUSE_LEAVE_HANDLER = new ComponentEventDescriptor("dataPointMouseLeaveHandler", "MouseLeave event for a datapoint (bar/column/marker/bubble/donut-slice).", JsonSchema.parse(Components.class.getResourceAsStream("/apexcharts.event.mouse.props.json")));
//    public static ComponentEventDescriptor ZOOMED_HANDLER = new ComponentEventDescriptor("zoomedHandler", "Fires when user zooms in/out the chart using either the selection zooming tool or zoom in/out buttons.", JsonSchema.parse(Components.class.getResourceAsStream("/apexcharts.event.xaxis.props.json")));
//    public static ComponentEventDescriptor SCROLLED_HANDLER = new ComponentEventDescriptor("scrolledHandler", "Fires when user scrolls using the pan tool.", JsonSchema.parse(Components.class.getResourceAsStream("/apexcharts.event.xaxis.props.json")));
//    public static ComponentEventDescriptor BRUSH_SCROLLED_HANDLER = new ComponentEventDescriptor("brushScrolledHandler", "Fires when user drags the brush in a brush chart.", JsonSchema.parse(Components.class.getResourceAsStream("/apexcharts.event.xaxis.props.json")));

    /**
     * Components register with the Java side ComponentRegistry but providing a ComponentDescriptor.  Here we
     * build the descriptor for this one component. Icons on the component palette are optional.
     */
    public static ComponentDescriptor DESCRIPTOR = ComponentDescriptorImpl.ComponentBuilder.newBuilder()
            .setPaletteCategory(Components.COMPONENT_CATEGORY)
            .setId(COMPONENT_ID)
            .setModuleId(Components.MODULE_ID)
            .setSchema(SCHEMA) //  this could alternatively be created purely in Java if desired
//            .setEvents(Arrays.asList(ANIMATION_END_HANDLER, BEFORE_MOUNT_HANDLER, MOUNTED_HANDLER, UPDATED_HANDLER, CLICK_HANDLER, MOUSE_MOVE_HANDLER, LEGEND_CLICK_HANDLER, MARKER_CLICK_HANDLER, SELECTION_HANDLER, DATA_POINT_SELECTION_HANDLER, DATA_POINT_MOUSE_ENTER_HANDLER, DATA_POINT_MOUSE_LEAVE_HANDLER, ZOOMED_HANDLER, SCROLLED_HANDLER, BRUSH_SCROLLED_HANDLER))
            .setName("EChart")
            .setIcon(new ImageIcon(Objects.requireNonNull(Components.class.getResource("/icons/echarts-logo.png"))))
            .addPaletteEntry("", "EChart MAC", "An Sample EChart component.", null, null)
            .addPaletteEntry("sample", "Sample Bar EChart MAC", "An Sample EChart component.", null, (new JsonParser()).parse(new InputStreamReader(Objects.requireNonNull(Components.class.getResourceAsStream("/variants/echart.sample.props.json")))).getAsJsonObject())
            .addPaletteEntry("radarSample", "Sample Radar EChart MAC", "An Sample EChart component.", null, (new JsonParser()).parse(new InputStreamReader(Objects.requireNonNull(Components.class.getResourceAsStream("/variants/echart.radar.sample.props.json")))).getAsJsonObject())
            .addPaletteEntry("customPieSample", "Custom Sample Pie EChart MAC", "An Sample EChart component.", null, (new JsonParser()).parse(new InputStreamReader(Objects.requireNonNull(Components.class.getResourceAsStream("/variants/echart.custom.pie.sample.props.json")))).getAsJsonObject())
            .setDefaultMetaName("baseEChart")
            .setResources(Components.BROWSER_RESOURCES)
            .build();

}
