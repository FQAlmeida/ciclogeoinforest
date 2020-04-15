package com.udesc.dcc.bdes.ciclogeoinforest.negocio;

import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.DadosLayer;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.FeatureInfo;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.NaoGeografico;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Usuario;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.RestData.*;
import java.awt.Image;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Leandro Lisura
 */
@Path("server")
public class ServidorTrilhas {

    Edicao editor = new Edicao();
    Visualizacao visualizador = new Visualizacao();
    ServidorMapas servidorMapas = new ServidorMapas();

    /*
     * Operações de Serviço Web:
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cidade")
    public java.util.List<com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Cidade> getCidades() {
        return visualizador.getCidades();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("bairro")
    public List<com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Bairro> getBairros() {
        return visualizador.getBairros();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("dificuldade")
    public List<com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Dificuldade> getDificuldades() {
        return visualizador.getDificuldades();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("superficie")
    public List<com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Superficie> getSuperficies() {
        return visualizador.getSuperficies();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("regiao")
    public List<com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Regiao> getRegioes() {
        return visualizador.getRegioes();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("categoria")
    public List<com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Categoria> getCategorias() {
        return visualizador.getCategorias();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tipo")
    public List<com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Tipo> getTipos() {
        return visualizador.getTipos();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("layer/{codt}")
    public java.util.List<DadosLayer> downloadLayer(@PathParam("codt") java.util.List<java.lang.Integer> codt) {
        return visualizador.downloadLayer(codt);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("naogeografico")
    public java.util.List<NaoGeografico> getNaoGeografico(@QueryParam("tipo") String tipo, @QueryParam("cod") Integer cod) {
        System.out.println("tipo: " + tipo + ", cod: " + cod);
        return visualizador.getNaoGeografico(tipo, cod);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("trilha/{codt}")
    public int getDataTrilha(@PathParam("codt") Integer codt) {
        System.out.println(codt);
        return visualizador.getDataTrilha(codt);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("trilha")
    public java.util.List<FeatureInfo> buscaTrilhas(@QueryParam("tipo") List<java.lang.Integer> tipo, @QueryParam("dificuldade") List<java.lang.Integer> dificuldade, @QueryParam("bairro") java.util.List<java.lang.Integer> bairro, @QueryParam("regiao") java.util.List<java.lang.Integer> regiao, @QueryParam("superficie") java.util.List<java.lang.Integer> superficie, @QueryParam("categoria") java.util.List<java.lang.Integer> categoria) {
        System.out.println("dados: " + tipo + "," + dificuldade + "," + bairro +  "," + regiao + "," +superficie + "," + categoria);
        return visualizador.buscaTrilhas(tipo, dificuldade, bairro, regiao, superficie, categoria);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("feature")
    public java.util.List<FeatureInfo> getFeatureInfo(@QueryParam("bbox1") double bbox1, @QueryParam("bbox2") double bbox2, @QueryParam("bbox3") double bbox3, @QueryParam("bbox4") double bbox4, @QueryParam("x") int x, @QueryParam("y") int y, @QueryParam("srs") int srs, @QueryParam("widthh") int widthh, @QueryParam("heightt") int heightt) {
        System.out.println("chegou aki");
        return visualizador.getFeatureInfo(bbox1, bbox2, bbox3, bbox4, x, y, srs, widthh, heightt);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("auxiliar/{codigoTrilha}")
    public double[] getDadosAuxiliaresVisualizacao(@PathParam("codigoTrilha") int codigoTrilha) {
        return visualizador.getDadosAuxiliaresVisualizacao(codigoTrilha);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("route")
    public int getRoute(@QueryParam("lat_orig") double lat_orig, @QueryParam("lon_orig") double lon_orig,
        @QueryParam("lat_dest") double lat_dest, @QueryParam("lon_dest") double lon_dest) {
        return visualizador.getRoute( lat_orig, lon_orig, lat_dest, lon_dest );
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("feature/{latitude}/{longitude}")
    public java.util.List<FeatureInfo> getFeatureInfoProximo(@PathParam("latitude") double latitude, @PathParam("longitude") double longitude) {
        return visualizador.getFeatureInfoProximo(latitude,longitude);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("byteimage")
    public byte[] getByteImage(@QueryParam("codimg") int codimg, @QueryParam("codwp") int codwp) {
        return visualizador.getByteImage(codimg, codwp);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("image")
    public Image getImage(@QueryParam("codimg") int codimg, @QueryParam("codwp") int codwp) {
        return visualizador.getImage(codimg, codwp);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("kml/{codtr}")
    public byte[] getKMLFile(@PathParam("codtr") int codtr) {
        return visualizador.getKMLFile(codtr);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("map")
    public Image getMap(@QueryParam("srs") int srs, @QueryParam("transparent") boolean transparent, @QueryParam("tiled") boolean tiled, @QueryParam("bbox1") double bbox1, @QueryParam("bbox2") double bbox2, @QueryParam("bbox3") double bbox3, @QueryParam("bbox4") double bbox4, @QueryParam("widthh") int widthh, @QueryParam("heightt") int heightt, @QueryParam("cql_filter") java.util.List<java.lang.Integer> cql_filter) {
        return visualizador.getMap(srs, transparent, tiled, bbox1, bbox2, bbox3, bbox4, widthh, heightt, cql_filter);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("osm")
    public byte[] getOSMMap(@QueryParam("maxlat") double maxlat, @QueryParam("minlon") double minlon, @QueryParam("maxlon") double maxlon, @QueryParam("minlat") double minlat, @QueryParam("scale") int scale, @QueryParam("zoom") int zoom) {
        return servidorMapas.getOSMMap(maxlat, minlon, maxlon, minlat, scale, zoom);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("bairro")
    public Boolean insertBairro(Bairro bairro) {
        return editor.insertBairro(bairro.getNome(), bairro.getCid_cod());
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("bairro/{bai_cod}")
    public Boolean updateBairro(@PathParam("bai_cod") int bai_cod, Bairro bairro) {
        
        if (editor.findBairro(bai_cod) != null) {
            return editor.updateBairro(bai_cod, bairro.getNome(), bairro.getCid_cod());
        } else {
            System.out.println("Bairro inexistente");
            return false;
        }
      
    }

    @DELETE
    @Path("bairro/{bai_cod}")
    public Boolean deleteBairro(@PathParam("bai_cod") int bai_cod) {

        if (editor.findBairro(bai_cod) != null) {
            return editor.deleteBairro(bai_cod);
        } else {
            System.out.println("Bairro inexistente.");
            return false;
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("cidade")
    public Boolean insertCidade(Cidade cidade) {
            return editor.insertCidade(cidade.getNome(), cidade.getEst_cod());
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("cidade/{cid_cod}")
    public Boolean updateCidade(@PathParam("cid_cod") int cid_cod, Cidade cidade) {
        if (editor.findCidade(cid_cod) != null) {
            return editor.updateCidade(cid_cod, cidade.getNome(), cidade.getEst_cod());
        } else {
            System.out.println("Bairro inexistente.");
            return false;
        }
    }
    
    @DELETE
    @Path("cidade/{cid_cod}")
    public Boolean deleteCidade(@PathParam("cid_cod") int cid_cod) {
        if (editor.findCidade(cid_cod) != null) {
            return editor.deleteCidade(cid_cod);
        } else {
            System.out.println("Cidade inexistente.");
            return false;
        }
    }

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("usuario")
    public Boolean insertUsuario(UsuarioRest usuario) {
        return editor.insertUsuario(usuario.getEmail(), usuario.getNome(), usuario.getSenha()); 
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("usuario/{login}/{senha}")
    public Boolean updateUsuario(UsuarioRest usuario,@PathParam("login") String login,@PathParam("senha") String senha) {
        if (verificaLogin(login, senha)) {
            int usu_cod = editor.u.getUsuCodByLogin(login);
            return editor.updateUsuario(new Usuario(usu_cod, usuario.getEmail(), usuario.getNome(), usuario.getSenha()), login, senha);
        } else {
            System.out.println("Falha ao verificar login.");
            return false;
        }
    }

    @DELETE
    @Path("usuario/{login}/{senha}")
    public Boolean deleteUsuario(@PathParam("login") String login, @PathParam("senha") String senha) {
        if (verificaLogin(login, senha)) {
            int usu_cod = editor.u.getUsuCodByLogin(login);
            return editor.deleteUsuario(new Usuario(usu_cod));
        } else {
            System.out.println("Falha ao verificar login.");
            return false;
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("waypoint")
    public Boolean insertWaypoint(Waypoint waypoint) {
        if (verificaLogin(waypoint.getLogin(), waypoint.getSenha())) {
            int usu_cod = editor.u.getUsuCodByLogin(waypoint.getLogin());
            return editor.insertWaypoint(waypoint.getCodt(), waypoint.getDescricao(), waypoint.getNome(), waypoint.getGeometria(), usu_cod, waypoint.getImagens(), waypoint.getCategorias());
        } else {
            System.out.println("Falha ao verificar login.");
            return false;
        }
    }
    
    @DELETE
    @Path("waypoint/{waypoint_codwp}/{login}/{senha}")
    public Boolean deleteWaypoint(@PathParam("waypoint_codwp") int waypoint_codwp, @PathParam("login") String login, @PathParam("senha") String senha) {
        if (verificaLogin(login, senha)) {
            int usu_cod = editor.u.getUsuCodByLogin(login);
            if (editor.findWaypoint(waypoint_codwp) != null && 
               (editor.findWaypoint(waypoint_codwp).getUsuCod() == usu_cod ||
                usu_cod == 1)) {
                return editor.deleteWaypoint(waypoint_codwp);
            } else {
                System.out.println("Waypoint inexistente ou usuário sem privilégio.");
                return false;
            }
        } else {
            System.out.println("Falha ao verificar login.");
            return false;
        }
    }

    //Inserção de trilhas com o arquivo geográfico já interpretado;
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("oldtrilha")
    public Boolean old_insertTrilha(OldTrilha trilha) {
        if (verificaLogin(trilha.getLogin(), trilha.getSenha())) {
            int usu_cod = editor.u.getUsuCodByLogin(trilha.getLogin());
            return editor.insertTrilha(trilha.getComprimanto(), trilha.getDesnivel(), trilha.getNome(), trilha.getDescricao(), trilha.getGraf_altitude(), trilha.getTip_cod(), trilha.getDif_cod(), trilha.getCid_cod(), usu_cod, trilha.getGeometria(), trilha.getBairros(), trilha.getRegioes(), trilha.getSuperficies());
        } else {
            System.out.println("Falha ao verificar login.");
            return false;
        }
    }
    
    //Inserção de trilhas recebendo o arquivo geográfico como parâmetro.
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("trilha")
    public Boolean insertTrilha(Trilha trilha) {
        if (verificaLogin(trilha.getLogin(), trilha.getSenha())) {
            int usu_cod = editor.u.getUsuCodByLogin(trilha.getLogin());
            return editor.insertTrilha(new FileReader(trilha.getArquivo_trilha()),trilha.getGraf_altitude(), trilha.getTip_cod(), trilha.getDif_cod(), trilha.getCid_cod(), usu_cod, trilha.getDescricao(), trilha.getBairros(), trilha.getRegioes(), trilha.getSuperficies());
        } else {
            System.out.println("Falha ao verificar login.");
            return false;
        }
    }
    

        //Inserção de trilhas recebendo do android/mobile
    //retornara -1 em caso de fracasso, ou o codigo da trilha inserida em caso de sucesso
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("trilhamobile")
    public int insertTrilhaFromMobile(TrilhaMobile trilha) {
        if (verificaLogin(trilha.getLogin(), trilha.getSenha())) {
            int usu_cod = editor.u.getUsuCodByLogin(trilha.getLogin());
            //parse da string de altitudes, eh uma string no formato "[num1, num2, ..., numN]"
            String[] stringAltitudes = trilha.getAltitudes().substring(1, trilha.getAltitudes().length()-2).split(",\\s");
            ArrayList<Double> vetorAltitudes = new ArrayList<Double>();
            for(int i = 0; i < stringAltitudes.length; i++){
                vetorAltitudes.add(Double.valueOf(stringAltitudes[i]));
            }
            return editor.insertTrilha(trilha.getComprimento(), trilha.getDesnivel(), trilha.getNome(), trilha.getDescricao(), trilha.getTip_cod(), trilha.getDif_cod(), trilha.getCid_cod(), usu_cod, trilha.getGeometria(), trilha.getBairros(), trilha.getRegioes(), trilha.getSuperficies(), trilha.getLogin(), trilha.getTimestampString(), trilha.getGraficoAltitude(), vetorAltitudes);
        } else {
            System.out.println("Falha ao verificar login.");
            return -1;
        }
    }
    
    @DELETE
    @Path("trilha/{trilhadados_codt}/{login}/{senha}")
    public Boolean deleteTrilha(@QueryParam("trilhadados_codt") int trilhadados_codt, @QueryParam("login") String login, @QueryParam("senha") String senha) {
        if (verificaLogin(login, senha)) {
            int usu_cod = editor.u.getUsuCodByLogin(login);
            if (editor.findTrilha(trilhadados_codt) != null) {
                return editor.deleteTrilha(trilhadados_codt);
            } else {
                System.out.println("Trilha inexistente ou usuário sem privilégio.");
                return false;
            }
        } else {
            System.out.println("Falha ao verificar login.");
            return false;
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("login/{login}/{senha}")
    public Boolean verificaLogin(@PathParam("login") String login,@PathParam("senha") String senha) {
        return editor.u.verificaLogin(login, senha);
    }
    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("loginusuario/{login}/{senha}")
    public Usuario loginUsuario(@PathParam("login") String login, @PathParam("senha") String senha) {
        if(verificaLogin(login, senha)){
            return editor.u.getUsuByLogin(login);
        }
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("email/{email}")
    public Boolean existeEmail(@PathParam("email") String email) {
            return editor.u.existeEmail(email);
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("trilhauser{login}/{senha}/{cod}")
    public java.util.List<NaoGeografico> getTrilhaUser(@PathParam("login") String login, @PathParam("senha") String senha, @PathParam("cod") Integer cod) {
        if(verificaLogin(login, senha)){
            return visualizador.getTrilhaUser(login, cod);
        }else return null;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("trilhauser/{login}/{senha}")
    public java.util.List<FeatureInfo> getTrilhasUser(@PathParam("login") String login, @PathParam("senha") String senha) {
        if(verificaLogin(login, senha)){
            return visualizador.getTrilhasUser(login);
        }else return null;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("waypointuser/{login}/{senha}")
    public java.util.List<FeatureInfo> getWaypointsUser(@PathParam("login") String login, @PathParam("senha") String senha) {
        if(verificaLogin(login, senha)){
            return visualizador.getWaypointsUser(login);
        }else return null;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("waypointuser/{login}/{senha}/{cod}")
    public java.util.List<NaoGeografico> getWaypointUser(@PathParam("login") String login, @PathParam("cod") Integer cod, @PathParam("senha") String senha) {
        if(verificaLogin(login, senha)){
            return visualizador.getWaypointUser(cod);
        }else return null;
    }
    
}
