package com.macautoinc.widget.common;

import com.inductiveautomation.perspective.common.api.BrowserResource;

import java.util.Set;

public class Components {

    public static final String MODULE_ID = "com.macautoinc.widget";
    public static final String URL_ALIAS = "chart";
    public static final String COMPONENT_CATEGORY = "Gauge Widgets";
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
