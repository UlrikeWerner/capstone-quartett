import "./Info.scss";
import {useEffect, useState} from "react";
import {GAME_INFO_TEXTS} from "../../../../types/GAME_INFO_TEXTS.ts";

type InfoProps = {
    nextTurn: "PLAYER" | "OPPONENT" | undefined;
}
export default function Info(props: InfoProps) {
    const [infoText, setInfoText] = useState<string>("");
    const [instructionText, setInstructionText] = useState<string>("");
    useEffect(() => {
        getStartText();
        setInstructionText(GAME_INFO_TEXTS.startTurnInfo);
    }, [ props.nextTurn ]);

    function getStartText(): void {
        if(props.nextTurn === "PLAYER"){
            setInfoText(GAME_INFO_TEXTS.startPlayerTurn);
        }else if(props.nextTurn === "OPPONENT"){
            setInfoText(GAME_INFO_TEXTS.startOpponentTurn);
        }else {
            setInfoText(GAME_INFO_TEXTS.gameOver);
        }
    }

    return (
        <div className="gameInfo">
            <h2>{infoText}</h2>
            <p>{instructionText}</p>
        </div>
    );
}