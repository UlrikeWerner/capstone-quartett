import "./Card.scss";
import {CardDTO} from "../../../../types/CardDTO.ts";

type CardDeckProps = {
    type: "deck" | "movingCard" |"playCard";
    owner: "player" | "opponent";
    score?: number;
    cardContent?: CardDTO;
    isClickable?: boolean;
    isVisible?: boolean;
    drawCard?: () => void;
}

export default function Card(props: CardDeckProps) {
    function cardClick() {
        if(props.isClickable){
            //Todo: Kartenanimation einfügen
            if(props.drawCard){
                props.drawCard();
            }
        }
    }

    return (
        <>
            {
             (props.type === "deck")
                    ?
                        <div className={"card card-" + (props.score && props.score > 1 ? props.owner : "isEmpty")}></div>
                    : (props.type === "movingCard")
                        ?
                            <div className={"card card-absolute card-" +props.owner + " " +
                                (props.isClickable ? "card-clickable" : "card-display-none")}
                                 onClick={cardClick}>
                            </div>
                        :
                            <div className={props.isVisible ? "card card-absolute card-content border-" + props.owner : "card-display-none"}>
                                <h3>{props.cardContent?.name}</h3>
                                {
                                    props.cardContent?.attributes &&
                                    Object.entries(props.cardContent.attributes)
                                        .map((attribute: [string, string]) =>
                                            <div className="attributes-wrapper" key={attribute[0]}>
                                                <p>{attribute[0]}</p>
                                                <p>{attribute[1]}</p>
                                            </div>
                                        )
                                }
                            </div>
            }
        </>
    )
}