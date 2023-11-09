import "./basicButton.scss";

type BasicButtonProps = {
    readonly icon?: boolean;
    readonly border?: boolean;
    readonly tooltip?: string;
    readonly text: string;
    readonly buttonClick: () => void;
}

type BasicButtonSpecialProps = {
    readonly icon?: boolean;
    readonly border?: boolean;
    readonly tooltip?: string;
    readonly text: string;
    readonly functionValue: string;
    readonly buttonClick: (value: string) => void;
}
export default function BasicButton(props: BasicButtonProps | BasicButtonSpecialProps) {

    function className(): string {
        let result = "button";
        if(props.icon) {
            result += " icon-button";
        }
        if(props.border) {
            result += " button-border";
        }
        return result;
    }

    return (
        <button className={className()}
                type="button"
                title={props.tooltip}
                onClick={() => {
                    if ("functionValue" in props) {
                        props.buttonClick(props.functionValue);
                    } else {
                        props.buttonClick();
                    }
                }}
        >
            {props.text}
        </button>
    );
}