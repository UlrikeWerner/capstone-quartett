import {NflLogoAcronym} from "./TennesseeTitansLogo.tsx";

export type CardDTO = {
    name: string;
    logo: NflLogoAcronym;
    attributes: Record<string, string>;
}