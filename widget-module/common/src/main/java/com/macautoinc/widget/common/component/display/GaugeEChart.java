package com.macautoinc.widget.common.component.display;

import com.inductiveautomation.ignition.common.gson.JsonParser;
import com.inductiveautomation.perspective.common.api.ComponentDescriptor;
import com.inductiveautomation.perspective.common.api.ComponentDescriptorImpl;
import com.macautoinc.widget.common.Components;

import javax.swing.*;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * The {@code GaugeEChart} class represents a gauge-style EChart component within the MAC Auto Inc. widget module.
 * It extends the {@link BaseEChart} class, inheriting its basic properties and behaviors, while specifying
 * additional configurations and palette entries specific to gauge charts.
 * <p>
 * This class is responsible for defining the component ID, setting up the component descriptor, and registering
 * various gauge-specific variants that can be utilized within the perspective views.
 * </p>
 */
public class GaugeEChart extends BaseEChart {

    /**
     * The unique identifier for the gauge EChart component. This ID is used to register and reference
     * the component within the system.
     */
    public static String COMPONENT_ID = "macautoinc.display.gauge.echart";

    /**
     * The component descriptor for the gauge EChart. It includes configurations such as the palette category,
     * the component ID, module ID, schema, name, icon, and various palette entries representing different
     * gauge variants like basic, simple, speed, progress, etc.
     * <p>
     * Each palette entry defines a specific variant of the gauge chart, providing a title, description,
     * and a JSON object representing the variant's properties.
     * </p>
     */
    public static ComponentDescriptor DESCRIPTOR = ComponentDescriptorImpl.ComponentBuilder.newBuilder()
            .setPaletteCategory(Components.COMPONENT_CATEGORY)
            .setId(COMPONENT_ID)
            .setModuleId(Components.MODULE_ID)
            .setSchema(SCHEMA) // this could alternatively be created purely in Java if desired
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
