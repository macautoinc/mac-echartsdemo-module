package com.macautoinc.widget.gateway;

import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.perspective.common.api.ComponentRegistry;
import com.inductiveautomation.perspective.gateway.api.ComponentModelDelegateRegistry;
import com.inductiveautomation.perspective.gateway.api.PerspectiveContext;
import com.macautoinc.widget.common.Components;
import com.macautoinc.widget.common.component.display.GaugeWidget;
import com.macautoinc.widget.gateway.delegate.EChartModelDelegate;

import java.util.Optional;

/**
 * GatewayHook is the main class for the gateway scope of the custom widget module.
 * It extends AbstractGatewayModuleHook to integrate with the Ignition Gateway lifecycle,
 * handling module setup, startup, and shutdown processes.
 */
public class GatewayHook extends AbstractGatewayModuleHook {

    // Logger for logging information and errors.
    private static final LoggerEx log = LoggerEx.newBuilder().build(GatewayHook.class.getName());

    // Context objects for accessing various gateway functionalities.
    private GatewayContext gatewayContext;
    private PerspectiveContext perspectiveContext;
    private ComponentRegistry componentRegistry;
    private ComponentModelDelegateRegistry modelDelegateRegistry;

    /**
     * Sets up the module with the provided GatewayContext. This method is called during the module's setup phase.
     * @param context The GatewayContext for the module.
     */
    @Override
    public void setup(GatewayContext context) {
        this.gatewayContext = context;
        log.info("Setting up Custom Widget module.");
    }

    /**
     * Starts up the module. This method is called during the module's startup phase and is responsible for
     * registering custom components and model delegates with the Perspective module.
     * @param activationState The license state of the module at startup.
     */
    @Override
    public void startup(LicenseState activationState) {
        log.info("Starting up GatewayHook!");

        // Initialize context objects for component and model delegate registration.
        this.perspectiveContext = PerspectiveContext.get(this.gatewayContext);
        this.componentRegistry = this.perspectiveContext.getComponentRegistry();
        this.modelDelegateRegistry = this.perspectiveContext.getComponentModelDelegateRegistry();

        // Register custom components and model delegates if the registries are available.
        if (this.componentRegistry != null) {
            log.info("Registering Custom components.");
            this.componentRegistry.registerComponent(GaugeWidget.DESCRIPTOR);
        } else {
            log.error("Reference to component registry not found, Custom Components will fail to function!");
        }

        if (this.modelDelegateRegistry != null) {
            log.info("Registering model delegates.");
            this.modelDelegateRegistry.register(GaugeWidget.COMPONENT_ID, EChartModelDelegate::new);
        } else {
            log.error("ModelDelegateRegistry was not found!");
        }
    }

    /**
     * Shuts down the module. This method is called during the module's shutdown phase and is responsible for
     * unregistering custom components and model delegates.
     */
    @Override
    public void shutdown() {
        log.info("Shutting down Custom module and removing registered components.");
        if (this.componentRegistry != null) {
            this.componentRegistry.removeComponent(GaugeWidget.COMPONENT_ID);
        } else {
            log.warn("Component registry was null, could not unregister Rad Components.");
        }
        if (this.modelDelegateRegistry != null) {
            this.modelDelegateRegistry.remove(GaugeWidget.COMPONENT_ID);
        }
    }

    /**
     * Provides the mounted resource folder path for the module.
     * @return An Optional containing the path to the mounted resource folder.
     */
    @Override
    public Optional<String> getMountedResourceFolder() {
        return Optional.of("mounted");
    }

    /**
     * Provides the mount path alias for the module, allowing access to resources via a specific URL pattern.
     * @return An Optional containing the URL alias for the module's resources.
     */
    @Override
    public Optional<String> getMountPathAlias() {
        return Optional.of(Components.URL_ALIAS);
    }

    /**
     * Indicates whether the module is free or requires a license.
     * @return true if the module is free, false otherwise.
     */
    @Override
    public boolean isFreeModule() {
        return true;
    }
}
