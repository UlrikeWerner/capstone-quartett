import "./Card.scss";
import {CardDTO} from "../../../../types/CardDTO.ts";
import * as NFLIcons from "react-nfl-logos";
import {getTeamLogo} from "../../../../types/TeamLogo.tsx";

type CardDeckProps = {
    readonly type: "deck" | "movingCard" |"playCard";
    readonly owner: "player" | "opponent";
    readonly score?: number;
    readonly cardContent?: CardDTO;
    readonly isClickable?: boolean;
    readonly isVisible?: boolean;
    readonly drawCard?: () => void;
}

export default function Card(props: CardDeckProps) {

    function chooseCardType() {
        if(props.type === "deck"){
            return (
                <div className={"card card-" + (props.score && props.score > 1 ? props.owner : "isEmpty")}>
                    { props.score && props.score > 1 &&
                        // NOSONAR
                        <NFLIcons.NFL size={200} />
                    }
                </div>);
        }
        if(props.type === "movingCard"){
            return(
                <button className={"card-button card card-absolute card-" +props.owner + " " +
                    (props.isClickable ? "card-clickable" : "card-display-none")}
                        type="button" onClick={cardClick}>
                    { props.score && props.score > 1 &&
                        // NOSONAR
                        <NFLIcons.NFL size={200} />
                    }
                </button>);
        }

        return(
        <div className={props.isVisible ? "card card-absolute card-content border-" + props.owner : "card-display-none"}>
            <div className="card-logo">
                {getTeamLogo(props.cardContent?.logo ?? "NFL")}
            </div>
            <h3>{props.cardContent?.name}</h3>
            {
                props.cardContent?.attributes &&
                Object.entries(props.cardContent.attributes)
                    .map((attribute: [string, string]) =>
                        <button className={"attributes-wrapper attributes-border-" + props.owner} type="button" key={attribute[0]}>
                            <p>{attribute[0]}</p>
                            <p>{attribute[1]}</p>
                        </button>
                    )
            }
        </div>);
    }

    function cardClick() {
        if(props.isClickable && props.drawCard){
            props.drawCard();
        }
    }

    return (
        <>
            {chooseCardType()}
        </>
    )
}