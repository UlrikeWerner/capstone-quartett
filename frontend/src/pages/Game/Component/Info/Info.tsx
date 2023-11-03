import "./Info.scss";
import BasicButton from "../../../../Component/BasicButton.tsx";

type InfoProps = {
    readonly infoText: string;
    readonly instructionText: string;
    readonly showButton: boolean;
    readonly continueButtonClick: () => void;
}
export default function Info(props: InfoProps) {

    return (
        <div className="gameInfo">
            <h2>{props.infoText}</h2>
            <p>{props.instructionText}</p>
            {props.showButton &&
                <div className="button-wrapper">
                    <BasicButton text="weiter" buttonClick={props.continueButtonClick}/>
                </div>
            }
        </div>
    );
}