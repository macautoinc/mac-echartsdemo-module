package com.macautoinc.widget.gateway;

import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.perspective.common.api.ComponentRegistry;
import com.inductiveautomation.perspective.gateway.api.ComponentModelDelegateRegistry;
import com.inductiveautomation.perspective.gateway.api.PerspectiveContext;
import com.macautoinc.widget.common.Components;
import com.macautoinc.widget.gateway.delegate.EChartModelDelegate;
import com.macautoinc.widget.common.component.display.GaugeEChart;

import java.util.Optional;

public class GatewayHook extends AbstractGatewayModuleHook {

    private static final LoggerEx log = LoggerEx.newBuilder().build("macautoinc.echart.gateway.GatewayHook");

    private GatewayContext gatewayContext;
    private PerspectiveContext perspectiveContext;
    private ComponentRegistry componentRegistry;
    private ComponentModelDelegateRegistry modelDelegateRegistry;

    @Override
    public void setup(GatewayContext context) {
        this.gatewayContext = context;
        log.info("Setting up Custom Widget module.");
    }

    @Override
    public void startup(LicenseState activationState) {
        log.info("Starting up GatewayHook!");

        this.perspectiveContext = PerspectiveContext.get(this.gatewayContext);
        this.componentRegistry = this.perspectiveContext.getComponentRegistry();
        this.modelDelegateRegistry = this.perspectiveContext.getComponentModelDelegateRegistry();


        if (this.componentRegistry != null) {
            log.info("Registering Custom ECharts components.");
//            this.componentRegistry.registerComponent(BaseEChart.DESCRIPTOR);
            this.componentRegistry.registerComponent(GaugeEChart.DESCRIPTOR);
        } else {
            log.error("Reference to component registry not found, Custom Apex Charts Components will fail to function!");
        }

        if (this.modelDelegateRegistry != null) {
            log.info("Registering model delegates.");
//            this.modelDelegateRegistry.register(BaseEChart.COMPONENT_ID, EChartModelDelegate::new);
            this.modelDelegateRegistry.register(GaugeEChart.COMPONENT_ID, EChartModelDelegate::new);
        } else {
            log.error("ModelDelegateRegistry was not found!");
        }

    }

    @Override
    public void shutdown() {
        log.info("Shutting down Custom ECharts module and removing registered components.");
        if (this.componentRegistry != null) {
//            this.componentRegistry.removeComponent(BaseEChart.COMPONENT_ID);
            this.componentRegistry.removeComponent(GaugeEChart.COMPONENT_ID);
        } else {
            log.warn("Component registry was null, could not unregister Rad Components.");
        }
        if (this.modelDelegateRegistry != null) {
//            this.modelDelegateRegistry.remove(BaseEChart.COMPONENT_ID);
            this.modelDelegateRegistry.remove(GaugeEChart.COMPONENT_ID);
        }
    }

    @Override
    public Optional<String> getMountedResourceFolder() {
        return Optional.of("mounted");
    }


    // Lets us use the route http://<gateway>/res/apexcharts/*
    @Override
    public Optional<String> getMountPathAlias() {
        return Optional.of(Components.URL_ALIAS);
    }

    @Override
    public boolean isFreeModule() {
        return true;
    }
}
