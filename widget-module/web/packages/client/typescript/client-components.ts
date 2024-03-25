import {ComponentMeta, ComponentRegistry} from '@inductiveautomation/perspective-client';
import '../scss/main';
import {
    EChart,
    // EChartMeta,
    GaugeEChartMeta
} from './components/EChart';

// export so the components are referencable, e.g. `Components['Image']
export {EChart};

// as new components are implemented, import them, and add their meta to this array
const components: Array<ComponentMeta> = [
    // new EChartMeta(),
    new GaugeEChartMeta()
];

// iterate through our components, registering each one with the registry.  Don't forget to register on the Java side too!
components.forEach((c: ComponentMeta) => ComponentRegistry.register(c));
