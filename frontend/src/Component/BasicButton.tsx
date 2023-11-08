import "./basicButton.scss";

type BasicButtonProps = {
    readonly text: string;
    readonly buttonClick: () => void;
}
export default function BasicButton(props: BasicButtonProps) {

    return (
        <button className="button"
                type="button"
                onClick={props.buttonClick}
        >
            {props.text}
        </button>
    );
}