import "./dialog.scss";
import BasicButton from "../Buttons/BasicButton.tsx";

type DialogProps = {
    readonly open: boolean;
    readonly content: string;
    readonly buttonName: string;
    readonly buttonName2?: string;
    readonly buttonFunction: () => void;
    readonly buttonFunction2?: () => void;
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
            {(props.buttonName2 && props.buttonFunction2) &&
                <BasicButton
                    border={true}
                    text={props.buttonName2}
                    buttonClick={props.buttonFunction2}
                />
            }
        </dialog>
    );
}