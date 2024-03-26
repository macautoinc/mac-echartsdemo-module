import * as React from 'react';
import {
    ReactNode,
    useEffect, useRef
} from 'react';
import {
    AbstractUIElementStore,
    ComponentMeta,
    ComponentProps,
    ComponentStoreDelegate,
    // Dataset,
    // isPlainObject,
    makeLogger,
    PComponent,
    PlainObject,
    PropertyTree,
    ReactResizeDetector,
    ResizeDetectorRefreshRate,
    SizeObject
    // TypeCode
} from '@inductiveautomation/perspective-client';
import {bind} from 'bind-decorator';
import cleanDeep from "clean-deep";
import objectScan from "object-scan";
import ReactECharts from 'echarts-for-react';
import type {
    EChartsType,
    // ECharts,
    EChartsOption
    // SetOptionOpts
} from "echarts";
import EChartsReact from "echarts-for-react";

export const COMPONENT_TYPE = "macautoinc.display.echart";

const logger = makeLogger(COMPONENT_TYPE);

export interface EChartProps {
    option: EChartsOption;
}

// These match events in the Gateway side component delegate.
enum MessageEvents {
    MESSAGE_RESPONSE_EVENT = "echart-response-event"
    // MESSAGE_REQUEST_EVENT = "echart-request-event"
}

export class EChartGatewayDelegate extends ComponentStoreDelegate {
    private chart: ReactECharts | null = null;

    constructor(componentStore: AbstractUIElementStore) {
        super(componentStore);
    }

    // @bind
    // init(chart: ReactECharts) {
    //     if (chart) {
    //         this.chart = chart;
    //     }
    // }
    @bind
    init() {
        // logger.info(() => "TODO: initialize EChartsGatewayDelegate");
    }

    @bind
    handleEvent(eventName: string, eventObject: PlainObject): void {
        if (this.chart) {
            logger.debug(() => `Received '${eventName}' event!`);
            const {
                MESSAGE_RESPONSE_EVENT
                // MESSAGE_REQUEST_EVENT
            } = MessageEvents;

            const functionToCall = eventObject.functionToCall;

            logger.warn(() => `Event functionToCall ${functionToCall}`);

            switch (eventName) {
                case MESSAGE_RESPONSE_EVENT:

                    break;
                default:
                    logger.warn(() => `No delegate event handler found for event: ${eventName} in EChartGatewayDelegate`);
            }
        }
    }
}


export const EChart = (props: ComponentProps<EChartProps, any>) => {
    // destructuring props
    const {
        // type,
        option} = props?.props || {};
    const {
        emit,
        // componentEvents,
        store} = props;
    const {delegate} = store || {};

    const echartRef = useRef<EChartsReact | null>(null);

    const initDelegate = () => {
        if (delegate) {
            (delegate as EChartGatewayDelegate).init();
        }
    };

    const prepareOptions = (options) => {

        options = cleanDeep(options);

        objectScan(['**'], {
            filterFn: ({parent, property, value}) => {
                if (typeof value === 'string' && value)  {
                    const val = value.trim();
                    if (  (val.startsWith("function(") || val.startsWith("function ("))) {
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
        // ComponentDidMount Equivalent
        // logger.info(`Mounting EChart Dynamic`);

        // Start Gateway Delegate
        initDelegate();

        return () => {
            // Component WillUnmount equivalent
            // logger.info(`Unmounting EChart Dynamic`);
        };
    }, []);

    const handleOnResize = (width, height) => {
        if (echartRef?.current) {
            const echartInstance: EChartsType = echartRef?.current?.getEchartsInstance();
            echartInstance.resize({width, height});
        }
    };
    const renderComponent = (): ReactNode => {

        if (option && Object.keys(option).length === 0 && Object.getPrototypeOf(option) === Object.prototype) {
            return (
                <div {...emit()} />
            );
        } else {
            return (
                <div {...emit()}>
                    <ReactECharts ref={(e) => { echartRef.current = e; }} option={prepareOptions(option)}/>
                    <ReactResizeDetector
                        handleHeight={true}
                        handleWidth={true}
                        onResize={handleOnResize}
                        refreshMode="debounce"
                        refreshRate={ResizeDetectorRefreshRate.STANDARD}
                    />
                </div>
            );
        }
    };

    return (
        renderComponent()
    );
};

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

// TODO: Add a comment
export class GaugeEChartMeta extends EChartMeta {
    getComponentType(): string {
        return "macautoinc.display.gauge.echart";
    }
}

