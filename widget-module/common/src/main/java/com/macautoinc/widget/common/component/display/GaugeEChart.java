package com.macautoinc.widget.common.component.display;

import com.inductiveautomation.ignition.common.gson.JsonParser;
import com.inductiveautomation.perspective.common.api.ComponentDescriptor;
import com.inductiveautomation.perspective.common.api.ComponentDescriptorImpl;
import com.macautoinc.widget.common.Components;

import javax.swing.*;
import java.io.InputStreamReader;
import java.util.Objects;

public class GaugeEChart extends BaseEChart {
    public static String COMPONENT_ID = "macautoinc.display.gauge.echart";

    public static ComponentDescriptor DESCRIPTOR = ComponentDescriptorImpl.ComponentBuilder.newBuilder()
            .setPaletteCategory(Components.COMPONENT_CATEGORY)
            .setId(COMPONENT_ID)
            .setModuleId(Components.MODULE_ID)
            .setSchema(SCHEMA) //  this could alternatively be created purely in Java if desired
//            .setEvents(Arrays.asList(ANIMATION_END_HANDLER, BEFORE_MOUNT_HANDLER, MOUNTED_HANDLER, UPDATED_HANDLER, CLICK_HANDLER, MOUSE_MOVE_HANDLER, LEGEND_CLICK_HANDLER, MARKER_CLICK_HANDLER, SELECTION_HANDLER, DATA_POINT_SELECTION_HANDLER, DATA_POINT_MOUSE_ENTER_HANDLER, DATA_POINT_MOUSE_LEAVE_HANDLER, ZOOMED_HANDLER, SCROLLED_HANDLER, BRUSH_SCROLLED_HANDLER))
            .setName("EChart")
            .setIcon(new ImageIcon(Objects.requireNonNull(Components.class.getResource("/icons/echarts-logo.png"))))
            .addPaletteEntry("", "Gauge", "A Gauge EChart component.", null, null)
            .addPaletteEntry("basic", "Basic Gauge", "A Basic Gauge EChart component.", null, (new JsonParser()).parse(new InputStreamReader(Objects.requireNonNull(Components.class.getResourceAsStream("/variants/gauge/basic.props.json")))).getAsJsonObject())
            .addPaletteEntry("simple", "Simple Gauge", "A Simple Gauge EChart component.", null, (new JsonParser()).parse(new InputStreamReader(Objects.requireNonNull(Components.class.getResourceAsStream("/variants/gauge/simple.props.json")))).getAsJsonObject())
            .addPaletteEntry("speed", "Speed Gauge", "A Speed Gauge EChart component.", null, (new JsonParser()).parse(new InputStreamReader(Objects.requireNonNull(Components.class.getResourceAsStream("/variants/gauge/speed.props.json")))).getAsJsonObject())
            .addPaletteEntry("progress", "Progress Gauge", "A Progress Gauge EChart component.", null, (new JsonParser()).parse(new InputStreamReader(Objects.requireNonNull(Components.class.getResourceAsStream("/variants/gauge/progress.props.json")))).getAsJsonObject())
            .addPaletteEntry("stageSpeed", "Stage Speed Gauge", "A Stage Speed Gauge EChart component.", null, (new JsonParser()).parse(new InputStreamReader(Objects.requireNonNull(Components.class.getResourceAsStream("/variants/gauge/stage.speed.props.json")))).getAsJsonObject())
            .addPaletteEntry("grade", "Grade Gauge", "A Grade Gauge EChart component.", null, (new JsonParser()).parse(new InputStreamReader(Objects.requireNonNull(Components.class.getResourceAsStream("/variants/gauge/grade.props.json")))).getAsJsonObject())
            .addPaletteEntry("multiTitle", "Multi Title Gauge", "A Multi Title Gauge EChart component.", null, (new JsonParser()).parse(new InputStreamReader(Objects.requireNonNull(Components.class.getResourceAsStream("/variants/gauge/multi.title.props.json")))).getAsJsonObject())
            .addPaletteEntry("temperature", "Temperature Gauge", "A Temperature Gauge EChart component.", null, (new JsonParser()).parse(new InputStreamReader(Objects.requireNonNull(Components.class.getResourceAsStream("/variants/gauge/temperature.props.json")))).getAsJsonObject())
            .addPaletteEntry("ring", "Ring Gauge", "A Ring Gauge EChart component.", null, (new JsonParser()).parse(new InputStreamReader(Objects.requireNonNull(Components.class.getResourceAsStream("/variants/gauge/ring.props.json")))).getAsJsonObject())
            .addPaletteEntry("barometer", "Barometer Gauge", "A Barometer Gauge EChart component.", null, (new JsonParser()).parse(new InputStreamReader(Objects.requireNonNull(Components.class.getResourceAsStream("/variants/gauge/barometer.props.json")))).getAsJsonObject())
            .addPaletteEntry("clock", "Clock Gauge", "A Clock Gauge EChart component.", null, (new JsonParser()).parse(new InputStreamReader(Objects.requireNonNull(Components.class.getResourceAsStream("/variants/gauge/clock.props.json")))).getAsJsonObject())
            .addPaletteEntry("car", "Car Gauge", "A Car Gauge EChart component.", null, (new JsonParser()).parse(new InputStreamReader(Objects.requireNonNull(Components.class.getResourceAsStream("/variants/gauge/car.props.json")))).getAsJsonObject())
            .setDefaultMetaName("GaugeEChart")
            .setResources(Components.BROWSER_RESOURCES)
            .build();
}
