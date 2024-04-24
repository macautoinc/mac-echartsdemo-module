package com.macautoinc.widget.designer;

import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.designer.model.AbstractDesignerModuleHook;
import com.inductiveautomation.ignition.designer.model.DesignerContext;
import com.inductiveautomation.perspective.designer.DesignerComponentRegistry;
import com.inductiveautomation.perspective.designer.api.ComponentDesignDelegateRegistry;
import com.inductiveautomation.perspective.designer.api.PerspectiveDesignerInterface;
import com.macautoinc.widget.common.component.display.GaugeWidget;

/**
 * The 'hook' class for the designer scope of the module. This class is responsible for initializing
 * and registering custom components within the Ignition Designer environment. It extends the
 * AbstractDesignerModuleHook, which provides the basic structure for integrating with the Ignition Designer.
 */
public class DesignerHook extends AbstractDesignerModuleHook {
    // Logger for this class, used to log informational messages and errors.
    private static final LoggerEx logger = LoggerEx.newBuilder().build(DesignerHook.class.getName());

    // Static initializer block to add localization bundle for this module.
    static {
        BundleUtil.get().addBundle("macautoinc", DesignerHook.class.getClassLoader(), "echarts");
    }

    // Context of the designer, providing access to various functionalities and utilities within the designer.
    private DesignerContext context;
    // Registry for designer components, used to register custom components.
    private DesignerComponentRegistry registry;
    // Registry for component design delegates, not used in this class but available for future use.
    private ComponentDesignDelegateRegistry delegateRegistry;

    /**
     * Constructor logs the initialization message. Actual initialization happens in the startup method.
     */
    public DesignerHook() {
        logger.info("Registering ECharts Components in Designer!");
    }

    /**
     * Called when the module is started up. This method initializes the component registry and registers
     * the custom components.
     *
     * @param context The designer context, providing access to designer functionalities.
     * @param activationState The license state of the module.
     */
    @Override
    public void startup(DesignerContext context, LicenseState activationState) {
        this.context = context;
        init();
    }

    /**
     * Initializes the component registry and registers the GaugeWidget component.
     */
    private void init() {
        logger.debug("Initializing registry entrants...");

        // Obtain the PerspectiveDesignerInterface from the context, which provides access to the component registries.
        PerspectiveDesignerInterface pdi = PerspectiveDesignerInterface.get(context);

        // Initialize the component and delegate registries.
        registry = pdi.getDesignerComponentRegistry();
        delegateRegistry = pdi.getComponentDesignDelegateRegistry();

        // Register the GaugeWidget component with its descriptor.
        registry.registerComponent(GaugeWidget.DESCRIPTOR);
    }

    /**
     * Called when the module is shut down. This method unregisters the custom components.
     */
    @Override
    public void shutdown() {
        removeComponents();
    }

    /**
     * Unregisters the GaugeWidget component from the component registry.
     */
    private void removeComponents() {
        registry.removeComponent(GaugeWidget.COMPONENT_ID);
    }
}
