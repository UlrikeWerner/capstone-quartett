import "./Info.scss";
import FootballButton from "../../../../Component/Buttons/FootballButton.tsx";
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
                            <FootballButton text="weiter" buttonClick={props.continueButtonClick}/>
                        :
                            <FootballButton text="Lobby" buttonClick={() => navigate('/lobby')}/>
                    }
                </div>
            }
        </div>
    );
}