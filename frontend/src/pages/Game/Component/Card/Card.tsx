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
    readonly playCardIsClickable?: boolean;
    readonly isVisible?: boolean;
    readonly isLaidOut?: boolean;
    readonly drawCard?: () => void;
    readonly canChooseCategory?: boolean;
    readonly cardsInDeck?: number;
    readonly selectCategory?: (category: string) => void;
    readonly seeOpponentCard?: () => void;
}

export default function Card(props: CardDeckProps) {

    function renderDeck() {
        return (
            <div className={"card card-" + ((props.cardsInDeck && props.cardsInDeck > 0) ? props.owner : "isEmpty")}>
                {
                    props.cardsInDeck &&
                    (props.cardsInDeck > 0)
                        ? <NFLIcons.NFL size={200}/>
                        : ""
                }
            </div>);
    }

    function renderDeckTopCard() {
        return (
            <button className={"card-button card card-absolute card-" +
                (props.isClickable && props.cardsInDeck && props.cardsInDeck > 0 ? props.owner : "isEmpty") + " " +
                (props.isClickable ? "card-clickable" : "card-display-none")}
                    type="button" onClick={cardClick}>
                {props.score &&
                (props.score > 0)
                    ? <NFLIcons.NFL size={200}/>
                    : ""
                }
            </button>);
    }

    function renderPlayedCard() {
        return (
            <>
                {props.isLaidOut &&
                    (props.playCardIsClickable
                    ?
                        <button type="button"
                              className={"card card-absolute card-clickable card-" + props.owner}
                              onClick={props.seeOpponentCard}
                        >
                            <NFLIcons.NFL size={200}/>
                        </button>
                    :
                        <div className={"card card-absolute card-" + props.owner}>
                            <NFLIcons.NFL size={200}/>
                        </div>)
                }
                {!props.isLaidOut &&
                    <div
                        className={props.isVisible ? "card card-absolute card-content border-" + props.owner : "card-display-none"}>
                        <div className="card-logo">
                            {getTeamLogo(props.cardContent?.logo ?? "NFL")}
                        </div>
                        <h3>{props.cardContent?.name}</h3>
                        {
                            props.cardContent?.attributes &&
                            Object.entries(props.cardContent.attributes)
                                .map((attribute: [string, string]) =>
                                    <button className={
                                        props.canChooseCategory
                                            ? "attributes-wrapper attributes-border-" + props.owner
                                            : "attributes-wrapper"
                                    }
                                            type="button"
                                            key={attribute[0]}
                                            onClick={() => selectCategory(attribute[0])}
                                    >
                                        <p>{attribute[0]}</p>
                                        <p>{attribute[1]}</p>
                                    </button>
                                )
                        }
                    </div>
                }
            </>
        );
    }

    function chooseCardType() {
        if(props.type === "deck"){
            return renderDeck();
        }
        if(props.type === "movingCard"){
            return renderDeckTopCard();
        }

        return renderPlayedCard();
    }

    function cardClick() {
        if(props.isClickable && props.drawCard){
            props.drawCard();
        }
    }

    function selectCategory(category: string){
        if(props.selectCategory && props.canChooseCategory){
            props.selectCategory(category);
        }
    }

    return (
        <>
            {chooseCardType()}
        </>
    )
}