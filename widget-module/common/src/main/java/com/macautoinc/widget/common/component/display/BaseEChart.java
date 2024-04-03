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

    /**
     * Components register with the Java side ComponentRegistry but providing a ComponentDescriptor.  Here we
     * build the descriptor for this one component. Icons on the component palette are optional.
     */
    public static ComponentDescriptor DESCRIPTOR = ComponentDescriptorImpl.ComponentBuilder.newBuilder()
            .setPaletteCategory(Components.COMPONENT_CATEGORY)
            .setId(COMPONENT_ID)
            .setModuleId(Components.MODULE_ID)
            .setSchema(SCHEMA) //  this could alternatively be created purely in Java if desired
            .setName("EChart")
            .setIcon(new ImageIcon(Objects.requireNonNull(Components.class.getResource("/icons/echarts-logo.png"))))
            .addPaletteEntry("", "EChart MAC", "An Sample EChart component.", null, null)
            .setDefaultMetaName("baseEChart")
            .setResources(Components.BROWSER_RESOURCES)
            .build();
}
