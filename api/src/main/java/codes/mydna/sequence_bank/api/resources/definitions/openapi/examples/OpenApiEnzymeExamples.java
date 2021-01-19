package codes.mydna.sequence_bank.api.resources.definitions.openapi.examples;

public class OpenApiEnzymeExamples {

    public static final String EXAMPLE_ID = "754b527b-9c55-4d3d-bbef-86cb63ccbd0e";

    public static class Requests {

        public static final String INSERT_ENZYME = "{\n" +
                "    \"name\":\"Enzyme name\",\n" +
                "    \"sequence\":{\n" +
                "        \"value\":\"ACTG\"\n" +
                "    },\n" +
                "    \"upperCut\": 3\n" +
                "\n" +
                "}";

        public static final String UPDATE_ENZYME = "{\n" +
                "    \"name\":\"New enzyme name\",\n" +
                "    \"sequence\":{\n" +
                "        \"value\":\"GACT\"\n" +
                "    },\n" +
                "    \"upperCut\": 2\n" +
                "\n" +
                "}";

    }

    public static class Responses {

        public static final String GET_ENZYMES = "[\n" +
                "    {\n" +
                "        \"id\": \"" + EXAMPLE_ID + "\",\n" +
                "        \"created\": \"2020-12-26T18:43:32.398Z[UTC]\",\n" +
                "        \"lastModified\": \"2020-12-26T18:46:02.244Z[UTC]\",\n" +
                "        \"name\": \"New enzyme name\"\n" +
                "    }\n" +
                "]";

        public static final String GET_ENZYME = "{\n" +
                "    \"id\": \"" + EXAMPLE_ID + "\",\n" +
                "    \"created\": \"2020-12-26T18:43:32.398Z[UTC]\",\n" +
                "    \"lastModified\": \"2020-12-26T18:46:02.244Z[UTC]\",\n" +
                "    \"name\": \"New enzyme name\",\n" +
                "    \"sequence\": {\n" +
                "        \"value\": \"GACT\"\n" +
                "    },\n" +
                "    \"lowerCut\": 2,\n" +
                "    \"upperCut\": 2\n" +
                "}";

        public static final String INSERT_ENZYME = "{\n" +
                "    \"id\": \"" + EXAMPLE_ID + "\",\n" +
                "    \"created\": \"2020-12-26T18:43:32.398Z[UTC]\",\n" +
                "    \"lastModified\": \"2020-12-26T18:43:32.398Z[UTC]\",\n" +
                "    \"name\": \"Enzyme name\",\n" +
                "    \"sequence\": {\n" +
                "        \"value\": \"ACTG\"\n" +
                "    },\n" +
                "    \"lowerCut\": 1,\n" +
                "    \"upperCut\": 3\n" +
                "}";

        public static final String UPDATE_ENZYME = "{\n" +
                "    \"id\": \"" + EXAMPLE_ID + "\",\n" +
                "    \"created\": \"2020-12-26T18:43:32.398Z[UTC]\",\n" +
                "    \"lastModified\": \"2020-12-26T18:46:02.244Z[UTC]\",\n" +
                "    \"name\": \"New enzyme name\",\n" +
                "    \"sequence\": {\n" +
                "        \"value\": \"GACT\"\n" +
                "    },\n" +
                "    \"lowerCut\": 2,\n" +
                "    \"upperCut\": 2\n" +
                "}";

        public static final String DELETE_ENZYME = "true";

    }


}
