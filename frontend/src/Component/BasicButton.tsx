import "./basicButton.scss";

type BasicButtonProps = {
    readonly icon?: boolean;
    readonly tooltip?: string;
    readonly text: string;
    readonly buttonClick: () => void;
}

type BasicButtonSpecialProps = {
    readonly icon?: boolean;
    readonly tooltip?: string;
    readonly text: string;
    readonly functionValue: string;
    readonly buttonClick: (value: string) => void;
}
export default function BasicButton(props: BasicButtonProps | BasicButtonSpecialProps) {

    return (
        <button className={props.icon ? "button icon-button" : "button"}
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