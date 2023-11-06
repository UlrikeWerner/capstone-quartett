import "./Info.scss";
import BasicButton from "../../../../Component/BasicButton.tsx";
import {useNavigate} from "react-router-dom";

type InfoProps = {
    readonly infoText: string;
    readonly instructionText: string;
    readonly showButton: boolean;
    readonly continueButton: boolean;
    readonly continueButtonClick: () => void;
}
export default function Info(props: InfoProps) {
    const navigate = useNavigate();

    return (
        <div className="gameInfo">
            <h2>{props.infoText}</h2>
            <p>{props.instructionText}</p>
            {props.showButton &&
                <div className="button-wrapper">
                    {props.continueButton
                        ?
                            <BasicButton text="weiter" buttonClick={props.continueButtonClick}/>
                        :
                            <BasicButton text="Lobby" buttonClick={() => navigate('/lobby')}/>
                    }
                </div>
            }
        </div>
    );
}