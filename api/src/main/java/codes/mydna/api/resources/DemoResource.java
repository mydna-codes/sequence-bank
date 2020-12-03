package codes.mydna.api.resources;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("demo")
public class DemoResource {

    @GET
    public Response demo(){
        DemoObject demoObject = new DemoObject();
        return Response.ok(demoObject).build();
    }

    class DemoObject {

        private String[] clani;
        private String opis_projekta;
        private String[] mikrostoritve;
        private String[] github;
        private String[] travis;
        private String[] jenkins;
        private String[] dockerhub;

        public DemoObject() {
            clani = new String[]{"dm6067"};
            opis_projekta = "Aplikacija za iskanje nizov v dna sekvencah in razne operazcije nad samimi sekvencami.";
            // TODO: Fix analysis "mikrostoritve url"
            mikrostoritve = new String[]{"https://sequence-bank.mydna.codes/v1/dna", "https://analysis.mydna.codes/v1/demo"};
            github = new String[]{"https://github.com/mydna-codes/sequence-bank", "https://github.com/mydna-codes/analysis"};
            jenkins = new String[]{"https://jenkins.din-cloud.com/job/mydnacodes/job/sequence-bank/", "https://jenkins.din-cloud.com/job/mydnacodes/job/analysis/"};
            travis = jenkins.clone();
            dockerhub = new String[]{"https://hub.docker.com/repository/docker/mydnacodes/sequence-bank", "https://hub.docker.com/repository/docker/mydnacodes/analysis"};
        }

        public String[] getClani() {
            return clani;
        }

        public void setClani(String[] clani) {
            this.clani = clani;
        }

        public String getOpis_projekta() {
            return opis_projekta;
        }

        public void setOpis_projekta(String opis_projekta) {
            this.opis_projekta = opis_projekta;
        }

        public String[] getMikrostoritve() {
            return mikrostoritve;
        }

        public void setMikrostoritve(String[] mikrostoritve) {
            this.mikrostoritve = mikrostoritve;
        }

        public String[] getGithub() {
            return github;
        }

        public void setGithub(String[] github) {
            this.github = github;
        }

        public String[] getTravis() {
            return travis;
        }

        public void setTravis(String[] travis) {
            this.travis = travis;
        }

        public String[] getJenkins() {
            return jenkins;
        }

        public void setJenkins(String[] jenkins) {
            this.jenkins = jenkins;
        }

        public String[] getDockerhub() {
            return dockerhub;
        }

        public void setDockerhub(String[] dockerhub) {
            this.dockerhub = dockerhub;
        }
    }

}
