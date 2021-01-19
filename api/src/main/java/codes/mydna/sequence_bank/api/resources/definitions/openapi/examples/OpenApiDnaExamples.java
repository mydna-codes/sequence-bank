package codes.mydna.sequence_bank.api.resources.definitions.openapi.examples;

public class OpenApiDnaExamples {

    public static final String EXAMPLE_ID = "d875ca2a-d829-4fda-890d-e47cf655ee4c";

    public static class Requests {

        public static final String INSERT_DNA = "{\n" +
                "    \"name\":\"Dna\",\n" +
                "    \"sequence\": {\n" +
                "        \"value\":\"AAAACCCCTTTTGGGGAAAACCCCTTTTGGGGAAAACCCCTTTTGGGGAAAACCCCTTTTGGGG\"\n" +
                "    }\n" +
                "}";

        public static final String UPDATE_DNA = "{\n" +
                "    \"name\":\"New Dna\",\n" +
                "    \"sequence\":{\n" +
                "        \"value\":\"AAAACCCCTTTTGGGGAAAACCCCTTTTGGGG\"\n" +
                "    }\n" +
                "}";

    }

    public static class Responses {

        public static final String GET_DNAS = "[\n" +
                "    {\n" +
                "        \"id\": \"584aa147-a84f-4c70-adc8-728f97bd98fb\",\n" +
                "        \"created\": \"2020-12-26T20:19:22.097Z[UTC]\",\n" +
                "        \"lastModified\": \"2020-12-26T20:19:22.097Z[UTC]\",\n" +
                "        \"name\": \"MyDna\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"" + EXAMPLE_ID + "\",\n" +
                "        \"created\": \"2020-12-26T20:19:41.708Z[UTC]\",\n" +
                "        \"lastModified\": \"2020-12-26T20:19:41.708Z[UTC]\",\n" +
                "        \"name\": \"Dna\"\n" +
                "    }\n" +
                "]";

        public static final String GET_DNA = "{\n" +
                "    \"id\": \"" + EXAMPLE_ID + "\",\n" +
                "    \"created\": \"2020-12-26T20:19:41.708Z[UTC]\",\n" +
                "    \"lastModified\": \"2020-12-26T20:19:41.708Z[UTC]\",\n" +
                "    \"name\": \"Dna\",\n" +
                "    \"sequence\": {\n" +
                "        \"value\": \"AAAACCCCTTTTGGGGAAAACCCCTTTTGGGGAAAACCCCTTTTGGGGAAAACCCCTTTTGGGG\"\n" +
                "    }\n" +
                "}";

        public static final String INSERT_DNA = "{\n" +
                "    \"id\": \"" + EXAMPLE_ID + "\",\n" +
                "    \"created\": \"2020-12-26T20:19:41.708Z[UTC]\",\n" +
                "    \"lastModified\": \"2020-12-26T20:19:41.708Z[UTC]\",\n" +
                "    \"name\": \"Dna\",\n" +
                "    \"sequence\": {\n" +
                "        \"value\": \"AAAACCCCTTTTGGGGAAAACCCCTTTTGGGGAAAACCCCTTTTGGGGAAAACCCCTTTTGGGG\"\n" +
                "    }\n" +
                "}";

        public static final String UPDATE_DNA = "{\n" +
                "    \"id\": \"" + EXAMPLE_ID + "\",\n" +
                "    \"created\": \"2020-12-26T20:19:41.708Z[UTC]\",\n" +
                "    \"lastModified\": \"2020-12-26T20:21:25.623Z[UTC]\",\n" +
                "    \"name\": \"New Dna\",\n" +
                "    \"sequence\": {\n" +
                "        \"value\": \"AAAACCCCTTTTGGGGAAAACCCCTTTTGGGG\"\n" +
                "    }\n" +
                "}";

        public static final String DELETE_DNA = "true";

    }

}
