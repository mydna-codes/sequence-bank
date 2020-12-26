package codes.mydna.lib.openapi.examples;

public class OpenApiGeneExamples {


    public static final String EXAMPLE_ID = "1ddc4200-a42f-4fed-a203-c54607e46af4";

    public static class Requests {

        public static final String INSERT_GENE = "{\n" +
                "    \"name\":\"Gene\",\n" +
                "    \"sequence\":{\n" +
                "        \"value\":\"ACTGACTGACTGACTGACTG\"\n" +
                "    }\n" +
                "}";

        public static final String UPDATE_GENE = "{\n" +
                "    \"name\": \"New gene\",\n" +
                "    \"sequence\":{\n" +
                "        \"value\":\"GTCAGTCAGTCAGTCAGTCA\"\n" +
                "    }\n" +
                "}";

    }

    public static class Responses {

        public static final String GET_GENES = "[\n" +
                "    {\n" +
                "        \"id\": \"7821b07b-e12b-4d15-aa2c-9aded673fd2f\",\n" +
                "        \"created\": \"2020-12-26T20:08:12.118Z[UTC]\",\n" +
                "        \"lastModified\": \"2020-12-26T20:08:12.118Z[UTC]\",\n" +
                "        \"name\": \"Gene\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"" + EXAMPLE_ID + "\",\n" +
                "        \"created\": \"2020-12-26T20:10:50.229Z[UTC]\",\n" +
                "        \"lastModified\": \"2020-12-26T20:10:50.229Z[UTC]\",\n" +
                "        \"name\": \"Gene\"\n" +
                "    }\n" +
                "]";

        public static final String GET_GENE = "{\n" +
                "    \"id\": \"" + EXAMPLE_ID + "\",\n" +
                "    \"created\": \"2020-12-26T20:10:50.229Z[UTC]\",\n" +
                "    \"lastModified\": \"2020-12-26T20:10:50.229Z[UTC]\",\n" +
                "    \"name\": \"Gene\",\n" +
                "    \"sequence\": {\n" +
                "        \"value\": \"ACTGACTGACTGACTGACTG\"\n" +
                "    }\n" +
                "}";

        public static final String INSERT_GENE = "{\n" +
                "    \"id\": \"" + EXAMPLE_ID + "\",\n" +
                "    \"created\": \"2020-12-26T20:10:50.229Z[UTC]\",\n" +
                "    \"lastModified\": \"2020-12-26T20:10:50.229Z[UTC]\",\n" +
                "    \"name\": \"Gene\",\n" +
                "    \"sequence\": {\n" +
                "        \"value\": \"ACTGACTGACTGACTGACTG\"\n" +
                "    }\n" +
                "}";

        public static final String UPDATE_GENE = "{\n" +
                "    \"id\": \"" + EXAMPLE_ID + "\",\n" +
                "    \"created\": \"2020-12-26T20:10:50.229Z[UTC]\",\n" +
                "    \"lastModified\": \"2020-12-26T20:15:08.74Z[UTC]\",\n" +
                "    \"name\": \"New gene\",\n" +
                "    \"sequence\": {\n" +
                "        \"value\": \"GTCAGTCAGTCAGTCAGTCA\"\n" +
                "    }\n" +
                "}";

        public static final String DELETE_GENE = "true";

    }

}
