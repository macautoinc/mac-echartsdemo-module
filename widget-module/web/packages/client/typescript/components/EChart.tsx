import * as React from 'react';
import { ReactNode, useEffect, useRef } from 'react';
import {
    AbstractUIElementStore,
    ComponentMeta,
    ComponentProps,
    ComponentStoreDelegate,
    makeLogger,
    PComponent,
    PlainObject,
    PropertyTree,
    ReactResizeDetector,
    SizeObject
} from '@inductiveautomation/perspective-client';
import { bind } from 'bind-decorator';
import cleanDeep from "clean-deep";
import objectScan from "object-scan";
import ReactECharts from 'echarts-for-react';
import type { EChartsType, EChartsOption } from "echarts";
import EChartsReact from "echarts-for-react";

// Define the component type for this custom widget
export const COMPONENT_TYPE = "macautoinc.display.widget";

// Create a logger specific for this component type
const logger = makeLogger(COMPONENT_TYPE);

// Interface defining the props specific to the EChart component
export interface EChartProps {
    option: EChartsOption;
}

// Enum for message events between the frontend and the gateway
// These match events in the Gateway side component delegate.

enum MessageEvents {
    MESSAGE_RESPONSE_EVENT = "echart-response-event"
}

/**
 * Gateway delegate class for the EChart component.
 * Handles communication and events between the frontend and the gateway.
 */
export class EChartGatewayDelegate extends ComponentStoreDelegate {
    private chart: ReactECharts | null = null;

    constructor(componentStore: AbstractUIElementStore) {
        super(componentStore);
    }

    @bind
    init() {
        // Initialization logic can be added here
    }

    @bind
    handleEvent(eventName: string, eventObject: PlainObject): void {
        if (this.chart) {
            logger.debug(() => `Received '${eventName}' event!`);
            const { MESSAGE_RESPONSE_EVENT } = MessageEvents;

            const functionToCall = eventObject.functionToCall;

            logger.warn(() => `Event functionToCall ${functionToCall}`);

            switch (eventName) {
                case MESSAGE_RESPONSE_EVENT:
                    // Handle the response event
                    break;
                default:
                    logger.warn(() => `No delegate event handler found for event: ${eventName} in EChartGatewayDelegate`);
            }
        }
    }
}

/**
 * The EChart component renders an ECharts chart using the provided options.
 * It supports dynamic resizing and updates based on the provided options prop.
 */
export const EChart = (props: ComponentProps<EChartProps, any>) => {
    const { option } = props?.props || {};
    const { emit, store } = props;
    const { delegate } = store || {};

    const echartRef = useRef<EChartsReact | null>(null);

    const initDelegate = () => {
        if (delegate) {
            (delegate as EChartGatewayDelegate).init();
        }
    };

    // Prepare the chart options, including converting stringified functions to actual functions
    const prepareOptions = (options) => {
        options = cleanDeep(options);

        objectScan(['**'], {
            filterFn: ({ parent, property, value }) => {
                if (typeof value === 'string' && value) {
                    const val = value.trim();
                    if (val.startsWith("function(") || val.startsWith("function (")) {
                        let fn = null;
                        eval("fn = " + value);
                        parent[property] = fn;
                    }
                }
            }
        })(options);
        return options;
    };

    useEffect(() => {
        // Initialize the delegate when the component mounts
        initDelegate();

        return () => {
            // Cleanup logic can be added here for when the component unmounts
        };
    }, []);

    // Handle chart resizing
    const handleOnResize = (width, height) => {
        if (echartRef?.current) {
            const echartInstance: EChartsType = echartRef?.current?.getEchartsInstance();
            echartInstance.resize({ width, height });
        }
    };

    // Render the ECharts component or a placeholder based on the provided options
    const renderComponent = (): ReactNode => {
        if (option && Object.keys(option).length === 0 && Object.getPrototypeOf(option) === Object.prototype) {
            return <div {...emit()} />;
        } else {
            return (
                <div {...emit()}>
                    <ReactECharts ref={(e) => { echartRef.current = e; }} option={prepareOptions(option)} />
                    <ReactResizeDetector
                        handleHeight={true}
                        handleWidth={true}
                        onResize={handleOnResize}
                        refreshMode="debounce"
                        refreshRate={33.33}
                    />
                </div>
            );
        }
    };

    return renderComponent();
};

/**
 * Meta class for the EChart component.
 * Provides metadata for the component including its type, view component, default size, delegate, and props reducer.
 */
export class EChartMeta implements ComponentMeta {
    getComponentType(): string {
        return COMPONENT_TYPE;
    }

    getViewComponent(): PComponent {
        return EChart;
    }

    getDefaultSize(): SizeObject {
        return ({
            width: 450,
            height: 300
        });
    }

    createDelegate(component: AbstractUIElementStore): ComponentStoreDelegate | undefined {
        return new EChartGatewayDelegate(component);
    }

    getPropsReducer(tree: PropertyTree): EChartProps {
        return {
            option: tree.read("option")
        };
    }
}

/**
 * Meta class for the GaugeEChart component, extending the EChartMeta.
 * Specifies a different component type for the gauge variant of the EChart component.
 */
export class GaugeEChartMeta extends EChartMeta {
    getComponentType(): string {
        return "macautoinc.display.widget.gauge";
    }
}
