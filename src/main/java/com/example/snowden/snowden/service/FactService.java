package com.example.snowden.snowden.service;

import java.util.ArrayList;
import java.util.List;
import com.example.snowden.snowden.utils.ComparisonsUtils;
import com.example.snowden.snowden.parsers.InputParse;
import com.example.snowden.snowden.parsers.WikiParseUtils;
import com.example.snowden.snowden.utils.FileOperation;
import com.example.snowden.snowden.utils.Logger;
import com.example.snowden.snowden.utils.NetworkUtils;
import org.jsoup.nodes.Document;

import com.example.snowden.snowden.Constants;
import com.example.snowden.snowden.models.Fact;


public class FactService {
    public void main(String[] args) {
        //processTrainData();
        processTestData();

        //Logger.log(preprocessFact("1","Nicky Wu is Saul Williams' better half.").toString());
    }

    private static void processTestData() {
        List<com.example.snowden.snowden.models.Fact> factsList = FileOperation.readTsvFile(Constants.TESTING_DATASET_INPUT_PATH_2020);
        List<Fact> resultFact = new ArrayList<>();
        for (com.example.snowden.snowden.models.Fact fact: factsList) {
            Fact resultfact = preprocessFact(fact.getId(), fact.getFact());
            Logger.log(resultfact.toString());
            resultFact.add(resultfact);
        }
        FileOperation.writeFile(resultFact,Constants.TESTING_DATASET_OUTPUT_PATH_2020);
    }

    private static void processTrainData() {
        List<com.example.snowden.snowden.models.Fact> factsList = FileOperation.readTsvFile(Constants.TRAINING_DATASET_INPUT_PATH_2020);
        List<Fact> resultFact = new ArrayList<>();
        for (com.example.snowden.snowden.models.Fact fact: factsList) {
            Fact resultfact = preprocessFact(fact.getId(), fact.getFact());
            Logger.log(resultfact.toString());
            resultFact.add(resultfact);
        }
        FileOperation.writeFile(resultFact,Constants.TRAINING_DATASET_OUTPUT_PATH_2020);
    }


    public static Fact preprocessFact(String id, String fact) {
        double result = 0.0;
        try {
            InputParse inputParse = new InputParse();
            com.example.snowden.snowden.models.Triplet triplet = inputParse.getTriplet(fact);
            if (!triplet.getSubject().isEmpty()) {
                List<String> alternativeUrls = WikiParseUtils.getAlternativeUrls(triplet.getSubject());
                for (String url : alternativeUrls) {
                    Logger.log(url);
                    Document document = NetworkUtils.getResponse(url);
                    List<String> infoboxrows = WikiParseUtils.getInfoboxrows(document);
                    if (!infoboxrows.isEmpty()) {
                        for (String row : infoboxrows) {
                            result = ComparisonsUtils.checkPredicateObject(triplet, row);
                            if (result != 0.0)
                                return new Fact(id, fact, result);
                        }

                        List<String> paragraphList = WikiParseUtils.fetchParagraph(document);
                        for (String paragraph: paragraphList) {
                             result = ComparisonsUtils.checkPredicateObject(triplet, paragraph);
                            if (result != 0.0)
                                return new Fact(id, fact, result);
                        }
                    } else {
                        List<String> paragraphList = WikiParseUtils.fetchParagraph(document);
                        for (String paragraph: paragraphList) {
                            result = ComparisonsUtils.checkPredicateObject(triplet, paragraph);
                            if (result != 0.0)
                                return new Fact(id, fact, result);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Fact(id, fact, result);
    }
}
