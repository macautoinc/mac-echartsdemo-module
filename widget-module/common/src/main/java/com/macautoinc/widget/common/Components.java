package com.macautoinc.widget.common;

import com.inductiveautomation.perspective.common.api.BrowserResource;

import java.util.Set;

/**
 * The {@code Components} class serves as a central repository for constants and configurations
 * related to the custom widget components developed for the MAC Auto Inc. widget module.
 * It includes definitions for module ID, URL aliases, component categories, and browser resources
 * required by the widget components.
 */
public class Components {

    /**
     * The unique identifier for the widget module. This ID is used to register and reference
     * the module within the system.
     */
    public static final String MODULE_ID = "com.macautoinc.widget";

    /**
     * The URL alias used for serving static resources related to the widget components.
     * This alias is appended to the base URL to form the full path to the resources.
     */
    public static final String URL_ALIAS = "chart";

    /**
     * The category under which the custom widget components are grouped in the component palette.
     * This helps in organizing and finding the components easily within the design environment.
     */
    public static final String COMPONENT_CATEGORY = "Gauge Widgets";

    /**
     * A set of {@link BrowserResource} objects representing the JavaScript and CSS resources
     * required by the widget components. These resources are loaded in the browser when
     * the components are used in a perspective view.
     */
    public static final Set<BrowserResource> BROWSER_RESOURCES =
            Set.of(
                    new BrowserResource(
                            "echarts-components-js",
                            String.format("/res/%s/Components.js", URL_ALIAS),
                            BrowserResource.ResourceType.JS
                    ),
                    new BrowserResource("echarts-components-css",
                            String.format("/res/%s/Components.css", URL_ALIAS),
                            BrowserResource.ResourceType.CSS
                    )
            );
}
