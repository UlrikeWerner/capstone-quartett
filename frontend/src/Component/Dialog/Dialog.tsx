import "./dialog.scss";
import BasicButton from "../Buttons/BasicButton.tsx";

type DialogProps = {
    open: boolean;
    content: string;
    buttonName: string;
    buttonFunction: () => void;
}

export default function Dialog(props: DialogProps) {

    return (
        <dialog open={props.open}
                className="dialog"
        >
            <div dangerouslySetInnerHTML={{__html: props.content}}></div>
            <BasicButton
                border={true}
                text={props.buttonName}
                buttonClick={props.buttonFunction}
            />
        </dialog>
    );
}