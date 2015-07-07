package br.ufes.inf.nemo.transparencia.controller;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
  
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import br.ufes.inf.nemo.transparencia.view.ViewInvestimentos;
 
@ManagedBean
@ViewScoped
public class ManageViewInvestimentosController implements Serializable {
     
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ViewInvestimentos investimento;

	private MapModel simpleModel;
  
    private Marker marker;
    
    @PersistenceContext
	private EntityManager entityManager;
  
    @PostConstruct
    public void init() {
        
        // Vetor de pins coloridos	
        String [] iconsMarker  = {        
        		"http://maps.google.com/mapfiles/ms/icons/blue-dot.png",
        		"http://maps.google.com/mapfiles/ms/icons/yellow-dot.png",
        		"http://maps.google.com/mapfiles/ms/icons/green-dot.png",
        		"http://maps.google.com/mapfiles/ms/icons/ltblue-dot.png",
        		"http://maps.google.com/mapfiles/ms/icons/orange-dot.png",
        		"http://maps.google.com/mapfiles/ms/icons/red-dot.png",
        		"http://maps.google.com/mapfiles/ms/icons/purple-dot.png",
        		"http://maps.google.com/mapfiles/ms/icons/pink-dot.png"
        };
        
        // Modelo do mapa
        simpleModel = new DefaultMapModel();
        
        // Reposit√≥rio com o nome do munic√≠pio e o cnpj
        Map<String, String> municipioCNPJ = montarRepositorioMunicipioCNPJ();
        
        // Reposit√≥rio com o nome do munic√≠pio, latitude, longitude e o n√∫mero de habitantes
        Map<String, Municipio> municipioLatLng = montarRepositorioMunicipioLatLng();
      
        // Contador para os pins coloridos
        int i = 0;

/*        
        // Adiciona os munic√≠pios no mapa
	    for(String key: municipioCNPJ.keySet())
	    {
	        simpleModel.addOverlay(
	        		new Marker(municipioLatLng.get(key).getCoordenadas(), 
	        				key, 
	        				municipioCNPJ.get(key) + ";" + municipioLatLng.get(key).getPopulacao(),
	        				iconsMarker[i]));
	        
	        // Altera o valor do contador dos pins
	        if (i == 7)
	        	i = 0;
	        else
	        	i++;	        
	    }
*/	    
     // Adiciona os munic√≠pios no mapa
	    for(String key: municipioLatLng.keySet())
	    {
	        simpleModel.addOverlay(
	        		new Marker(municipioLatLng.get(key).getCoordenadas(), 
	        				key, 
	        				municipioCNPJ.get(key) + ";" + municipioLatLng.get(key).getPopulacao(),
	        				iconsMarker[i]));
	        
	        // Altera o valor do contador dos pins
	        if (i == 7)
	        	i = 0;
	        else
	        	i++;	        
	    }
	    
    }
      
    public MapModel getSimpleModel() {
        return simpleModel;
    }
    
    public Marker getMarker() {
        return marker;
    }
      
    public ViewInvestimentos getInvestimento() {
		return investimento;
	}
    
    // A√ß√£o quando o pin √© selecionado
    public void onMarkerSelect(OverlaySelectEvent event) {
        
    	// Identifica o munic√≠pio selecionado
    	marker = (Marker) event.getOverlay();
        
		// Obt√©m CNPJ e n√∫mero da popula√ß√£o do munic√≠pio
		String [] dadosMunicipio = marker.getData().toString().split(";"); 
		
		// Obt√©m o total de investimentos no munic√≠pio
		String jpql = "select new br.ufes.inf.nemo.transparencia.view.ViewInvestimentos( " +
				"sum(p.valValorRepasse)) " +
				"from Pagamentos p where p.strCpfCnpjNis = " + dadosMunicipio[0];	
		
		// Executa a consulta
		TypedQuery<ViewInvestimentos> query = entityManager.createQuery(jpql, ViewInvestimentos.class);
		ViewInvestimentos result = query.getSingleResult();
		
		// Configura os dados a serem exibidos
		investimento = new ViewInvestimentos();
		investimento.setMunicipio(marker.getTitle());
		investimento.setTotalDeInvestimentos(result.getTotalDeInvestimentos());
		investimento.setTotalPopulacao(Integer.parseInt(dadosMunicipio[1]));
		investimento.setUrl("?cnpj=" + dadosMunicipio[0] + "&municipio=" + marker.getTitle());
    }    

    // Retorna dicion√°rio com o nome do munic√≠pio e latitude e longitude 
	private Map<String, Municipio> montarRepositorioMunicipioLatLng() {	

		// Monta a consulta sparql
		String query = 
				"PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> " +
			    "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#> " +
				"PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
			    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"SELECT DISTINCT ?name ?lat ?long ?pop " +
				"WHERE " +
				"{ " +
				"    { " +
				"        ?city rdfs:label ?name ; " +
				"            dbpedia-owl:populationTotal ?pop ; " +
				"            dbpedia-owl:isPartOf <http://dbpedia.org/resource/Esp%C3%ADrito_Santo> ; " +
//				"            dbpprop:subdivisionName <http://dbpedia.org/resource/Esp%C3%ADrito_Santo> ; " +				
				"            geo:lat ?lat ; " +
				"            geo:long ?long. " +
				"            FILTER (langMatches(lang(?name), \"EN\")) . " +
				"    } " +
				"UNION " +
				"    { " +
				"       ?city rdfs:label ?name ; " +
				"            dbpedia-owl:populationTotal ?pop ; " +
				"            rdf:type <http://dbpedia.org/class/yago/PopulatedPlacesInEsp%C3%ADritoSanto> ; " +
				"            geo:lat ?lat ; " +
				"            geo:long ?long. " +
				"            FILTER (langMatches(lang(?name), \"EN\")) . " +
				"    } " +
				"} " +
				"ORDER BY ?name ";
		
		//query = consultaSparql();

		// Vari√°veis para preenchimento do mapa
		Map<String, Municipio> dictionary = new HashMap<String, Municipio>();
		Municipio municipio;
		String nomeMunicipio;
		
		// Executa a consulta sparql
		QueryExecution queryExecution = 
				QueryExecutionFactory.sparqlService(
						"http://dbpedia.org/sparql", 
						query);
		ResultSet results = queryExecution.execSelect();
		
		// Percorre o resultset da consulta sparql para preencher o mapa
		while (results.hasNext()) {
			QuerySolution solution = results.next();		
			
			// Dados do munic√≠pio
			nomeMunicipio = solution.getLiteral("name").getValue().toString();
			municipio = new Municipio();
			municipio.setCoordenadas(new LatLng(
					solution.getLiteral("lat").getFloat(),
					solution.getLiteral("long").getFloat()));
			municipio.setPopulacao(solution.getLiteral("pop").getInt());
			
			// Retira sufixo do estado 
			nomeMunicipio = nomeMunicipio.replace(", Esp√≠rito Santo", "");
			
			// Adiciona os dados no mapa
			dictionary.put(nomeMunicipio,municipio);
		}			
		
		return dictionary;			
	}

	// Retorna dicion√°rio com o nome do munic√≠pio e cnpj
	private Map<String, String> montarRepositorioMunicipioCNPJ() {
		Map<String, String> dictionary = new HashMap<String, String>();
		dictionary.put("Afonso Cl·udio", "27165562000141");
		dictionary.put("¡gua Doce do Norte", "31796626000180");
		dictionary.put("¡guia Branca", "31796584000187");
		dictionary.put("Alegre", "27174101000135");
		dictionary.put("Alfredo Chaves", "27142686000101");
		dictionary.put("Alto Rio Novo", "31796659000120");
		dictionary.put("Anchieta", "27142694000158");
		dictionary.put("Apiac·", "27165604000144");
		dictionary.put("Aracruz", "27142702000166");
		dictionary.put("Atilio Vivacqua", "27165620000137");
		dictionary.put("Baixo Guandu", "27165737000110");
		dictionary.put("Barra de S„o Francisco", "27165745000167");
		dictionary.put("Boa EsperanÁa", "27167436000126");
		dictionary.put("Bom Jesus do Norte", "27167360000139");
		dictionary.put("Brejetuba", "1612674000100");
		dictionary.put("Cachoeiro de Itapemirim", "27165588000190");
		dictionary.put("Cariacica", "27150549000119");
		dictionary.put("Castelo", "27165638000139");
		dictionary.put("Colatina", "27165729000174");
		dictionary.put("ConceiÁ„o da Barra", "27174077000134");
		dictionary.put("ConceiÁ„o do Castelo", "27165570000198");
		dictionary.put("Divino de S„o Louren√ßo", "27174127000183");
		dictionary.put("Domingos Martins", "27150556000110");
		dictionary.put("Dores do Rio Preto", "27167386000187");
		dictionary.put("Ecoporanga", "27167311000104");
		dictionary.put("Fund„o", "27165182000107");
		dictionary.put("Governador Lindenberg", "4217786000154");
		dictionary.put("GuaÁuÌ≠", "27174135000120");
		dictionary.put("Guarapari", "27165190000153");
		dictionary.put("Ibatiba", "27744150000166");
		dictionary.put("IbiraÁu", "27165208000117");
		dictionary.put("Ibitirama", "31726490000131");
		dictionary.put("Iconha", "27165646000185");
		dictionary.put("Irupi", "36403954000192");
		dictionary.put("ItaguaÁu", "27167451000174");
		dictionary.put("Itapemirim", "27174168000170");
		dictionary.put("Itarana", "27104363000123");
		dictionary.put("I˙na", "27167394000123");
		dictionary.put("JaguarÈ", "27744184000150");
		dictionary.put("JerÙnimo Monteiro", "27165653000187");
		dictionary.put("Jo„o Neiva", "31776479000186");
		dictionary.put("Laranja da Terra", "31796097000114");
		dictionary.put("Linhares", "27167410000188");
		dictionary.put("MantenÛpolis", "27167345000190");
		dictionary.put("MarataÌzes", "1609408000128");
		dictionary.put("Marechal Floriano", "39385927000122");
		dictionary.put("Maril‚ndia", "27744176000104");
		dictionary.put("Mimoso do Sul", "27174119000137");
		dictionary.put("Montanha", "27174051000196");
		dictionary.put("Mucurici", "27174069000198");
		dictionary.put("Muniz Freire", "27165687000171");
		dictionary.put("Muqui", "27082403000183");
		dictionary.put("Nova VenÈcia", "27167428000180");
		dictionary.put("Pancas", "27174150000178");
		dictionary.put("Pedro Can·rio", "28539872000141");
		dictionary.put("Pinheiros", "27174085000180");
		dictionary.put("Pi˙ma", "27165695000118");
		dictionary.put("Ponto Belo", "1614334000118");
		dictionary.put("Presidente Kennedy", "27165703000126");
		dictionary.put("Rio Bananal", "27744143000164");
		dictionary.put("Rio Novo do Sul", "27165711000172");
		dictionary.put("Santa Leopoldina", "27165521000155");
		dictionary.put("Santa Maria de Jetib·", "36388445000138");
		dictionary.put("Santa Teresa", "27167444000172");
		dictionary.put("S„o Domingos do Norte", "36350312000172");
		dictionary.put("S„o Gabriel da Palha", "27174143000176");
		dictionary.put("S„o JosÈ do CalÁado", "27167402000131");
		dictionary.put("S„o Mateus", "27167477000112");
		dictionary.put("S„o Roque do Cana„", "1612865000171");
		dictionary.put("Serra", "27174093000127");
		dictionary.put("Sooretama", "1612155000141");
		dictionary.put("Vargem Alta", "31723570000133");
		dictionary.put("Venda Nova do Imigrante", "31723497000108");
		dictionary.put("Viana", "27165547000101");
		dictionary.put("Vila Pav„o", "36350346000167");
		dictionary.put("Vila ValÈrio", "1619232000195");
		dictionary.put("Vila Velha", "27165554000103");
		dictionary.put("VitÛria", "27142058000126");
		return dictionary;
	}
	
	private String consultaSparql(){
		File file = new File("sparql.txt");
		String linha = null;
		String consulta = null;
		
		try{

			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			while((linha = reader.readLine()) != null){
			    consulta += " " + linha;
			}
			fileReader.close();
			reader.close();
			
		}
		catch(Exception e)
		{		
		}
		
		return consulta;		
	}
	
	// Classe auxiliar para inserir os dados do munic√≠pio no reposit√≥rio
	public class Municipio{
		private String nome;
		private LatLng coordenadas;
		private int populacao;

		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public LatLng getCoordenadas() {
			return coordenadas;
		}
		public void setCoordenadas(LatLng coordenadas) {
			this.coordenadas = coordenadas;
		}
		public int getPopulacao() {
			return populacao;
		}
		public void setPopulacao(int populacao) {
			this.populacao = populacao;
		}		
	}
}