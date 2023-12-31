export const GAME_INFO_TEXTS = {
    startPlayerTurn: "Du bist dran!",
    startTurnInfo: "Klick auf deinen Kartenstarpel, um dir deine Karte anzusehen.",
    chooseCategory: "Wähle eine Kategorie.",
    startChooseCategoryInfo: "Klick auf eine Kategorie auf deiner Karte, um sie zu wählen.",
    resultChosenCategoryContinue: "Klick auf deinen Kartenstarpel, um die nächste Runde zu beginnen",
    startOpponentTurn: "Dein Gegner ist am Zug!",
    seeOpponentCard: "Klick auf die gegnerischen Karte!",
    gameOver: "Das Spiel ist vorbei!"
}

export function getStartText(nextTurn: "PLAYER" | "OPPONENT" | undefined): string {
    if(nextTurn === "PLAYER"){
        return GAME_INFO_TEXTS.startPlayerTurn;
    }else if(nextTurn === "OPPONENT"){
        return GAME_INFO_TEXTS.startOpponentTurn;
    }else {
        return GAME_INFO_TEXTS.gameOver;
    }
}
