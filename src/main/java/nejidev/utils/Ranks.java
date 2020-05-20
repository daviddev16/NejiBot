package nejidev.utils;

import org.apache.commons.lang3.StringUtils;

public class Ranks {

    public enum RankNames {

        APRENDIZ,
        INICIANTE,
        AJUDANTE,
        ORGANIZADOR,
        PPROGRAMADOR_DA_ELITE,
        PROGRAMADOR_MASTER

    }

    public static String get(int issues){
        RankNames name = RankNames.APRENDIZ;
        if(issues > 500){
            name = RankNames.PROGRAMADOR_MASTER;
        }
        else if(issues > 200) {
            name = RankNames.PPROGRAMADOR_DA_ELITE;
        }
        else if(issues > 100) {
            name = RankNames.ORGANIZADOR;
        }
        else if(issues > 50) {
            name = RankNames.AJUDANTE;
        }
        else if(issues > 10) {
            name = RankNames.INICIANTE;
        }
        return normalize(name);
    }

    public static String normalize(RankNames names){
        return StringUtils.capitalize(names.name().toLowerCase().replaceAll("_", " ")).trim();
    }

}
