import "./dialog.scss";
import BasicButton from "../Buttons/BasicButton.tsx";

type DialogProps = {
    readonly open: boolean;
    readonly content: string;
    readonly buttonName: string;
    readonly buttonFunction: () => void;
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