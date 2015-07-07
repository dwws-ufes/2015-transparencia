package br.ufes.inf.nemo.transparencia.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDFS;

import br.ufes.inf.nemo.transparencia.domain.Pagamentos;


/**
 * Servlet implementation class ListPackagesInRdfServlet
 */
@WebServlet("/dados/municipios")
public class DespesasRdfServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;	

	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public DespesasRdfServlet() {
        super();
    }
    
    @PersistenceContext
	private EntityManager entityManager;	
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
				
		response.setContentType("text/xml");
		
		// Obtém os valores passados via querystring
		String cnpj = request.getParameter("cnpj");
        String municipio = request.getParameter("municipio");
        
        // Cria o model utilizando o Jena
     	Model model = null;        
     	
     	// Gera o RDF
     	// 1- GET sem parâmetro gera o RDF com as referências para os municípios
     	// 2- GET com CNPJ e Município gera o  RDF com as despesas do município
        if (cnpj == null && municipio == null){
        	model = gerarRdfTodosMunicipio();
        }
        else if(!(cnpj == null) && !(municipio == null)){
        	model = gerarRdfPorMunicipio(cnpj, municipio);	
        }
        else{
        	throw new ServletException("Favor informar o CNPJ e nome do Município.");
        }
		
		try (PrintWriter out = response.getWriter()){
			model.write(out, "RDF/XML");			
		}
	}

	// Gera o RDF com os dados do município informado
	private Model gerarRdfPorMunicipio(String cnpj, String municipio)
			throws IOException {
				
		// Obtém os dados de despesas da prefeitura selecionada
		String jpql = "select new br.ufes.inf.nemo.transparencia.domain.Pagamentos " +
				"(p.id, p.strNomeFavorecido, p.valValorRepasse, p.datEmissaoOrdemBancaria) " +
				"from Pagamentos p where p.strCpfCnpjNis = " + cnpj + " " + 
				"order by p.datEmissaoOrdemBancaria";
		TypedQuery<Pagamentos> query = entityManager.createQuery(jpql, Pagamentos.class);
		List<Pagamentos> listaPagamentos = query.getResultList();
		
		// Cria o model utilizando o Jena
		Model model = ModelFactory.createDefaultModel();
		
		// Namespaces
		String myNS = "http://localhost:8080/Transparencia/dados/municipios/" + municipio + "/";
		String paymentNS = "http://reference.data.gov.uk/def/payment#";
		model.setNsPrefix("payment", paymentNS);
		
		// Cria propriedades
		Property paymentDate = ResourceFactory.createProperty(paymentNS + "date" );
		Property paymentNetAmount = ResourceFactory.createProperty(paymentNS + "netAmount" );
		Property paymentPayee = ResourceFactory.createProperty(paymentNS + "payee" );
		Property paymentReference = ResourceFactory.createProperty(paymentNS + "reference" );
			
		// Gera o RDF com as informações do identificador, favorecido, valor e data do pagamento
		// Exemplo: Município de Vitória
		//	<rdf:Description rdf:about="http://localhost:8080/Transparencia/dados/municipios/Vitória/161347">
		//		<payment:reference rdf:datatype="http://www.w3.org/2001/XMLSchema#long">161347</payment:reference>
		//		<payment:payee rdf:datatype="http://www.w3.org/2001/XMLSchema#string">PREF MUNIC DE VITORIA</payment:payee>
		//		<payment:netAmount rdf:datatype="http://www.w3.org/2001/XMLSchema#float">4333.0</payment:netAmount>
		//		<payment:date rdf:datatype="http://www.w3.org/2001/XMLSchema#dateTime">2014-12-11</payment:date>
		//	</rdf:Description>
		for (Pagamentos pagamento : listaPagamentos){
			
			Literal dataPagamento = ResourceFactory.createTypedLiteral(
					DF.format(pagamento.getDatEmissaoOrdemBancaria()),XSDDatatype.XSDdateTime);	
			
			Literal valorPagamento = ResourceFactory.createTypedLiteral(pagamento.getValValorRepasse());
			Literal favorecidoPagamento = ResourceFactory.createTypedLiteral(pagamento.getStrNomeFavorecido());
						
			model.createResource(myNS + pagamento.getId())
				.addLiteral(paymentDate, dataPagamento)
				.addLiteral(paymentNetAmount, valorPagamento)
				.addLiteral(paymentPayee, favorecidoPagamento)
				.addLiteral(paymentReference, pagamento.getId());
		}
		
		return model;
	}
	

	// Gera o RDF com os apontamentos para os municípios
	private Model gerarRdfTodosMunicipio() {
		
		// Dicionário com o nome do município e cnpj
		Map<String, String> municipioCNPJ = new HashMap<String, String>();
/*		
		municipioCNPJ.put("Baixo Guandu", "27165737000110");
		municipioCNPJ.put("Cachoeiro de Itapemirim", "27165588000190");
		municipioCNPJ.put("Colatina", "27165729000174");
		municipioCNPJ.put("Conceição da Barra", "27174077000134");
		municipioCNPJ.put("Domingos Martins", "27150556000110");
		municipioCNPJ.put("Dores do Rio Preto", "27167386000187");
		municipioCNPJ.put("Fundão", "27165182000107");
		municipioCNPJ.put("Guarapari", "27165190000153");
		municipioCNPJ.put("Iúna", "27167394000123");
		municipioCNPJ.put("João Neiva", "31776479000186");
		municipioCNPJ.put("Linhares", "27167410000188");
		municipioCNPJ.put("Mucurici", "27174069000198");
		municipioCNPJ.put("Piúma", "27165695000118");
		municipioCNPJ.put("Presidente Kennedy", "27165703000126");
		municipioCNPJ.put("Serra", "27174093000127");
		municipioCNPJ.put("Vila Velha", "27165554000103");
		municipioCNPJ.put("Vitória", "27142058000126");
*/
		
		municipioCNPJ.put("Afonso Cláudio", "27165562000141");
		municipioCNPJ.put("Água Doce do Norte", "31796626000180");
		municipioCNPJ.put("Águia Branca", "31796584000187");
		municipioCNPJ.put("Alegre", "27174101000135");
		municipioCNPJ.put("Alfredo Chaves", "27142686000101");
		municipioCNPJ.put("Alto Rio Novo", "31796659000120");
		municipioCNPJ.put("Anchieta", "27142694000158");
		municipioCNPJ.put("Apiacá", "27165604000144");
		municipioCNPJ.put("Aracruz", "27142702000166");
		municipioCNPJ.put("Atilio Vivaqua", "27165620000137");
		municipioCNPJ.put("Baixo Guandu", "27165737000110");
		municipioCNPJ.put("Barra de São Francisco", "27165745000167");
		municipioCNPJ.put("Boa Esperança", "27167436000126");
		municipioCNPJ.put("Bom Jesus do Norte", "27167360000139");
		municipioCNPJ.put("Brejetuba", "1612674000100");
		municipioCNPJ.put("Cachoeiro de Itapemirim", "27165588000190");
		municipioCNPJ.put("Cariacica", "27150549000119");
		municipioCNPJ.put("Castelo", "27165638000139");
		municipioCNPJ.put("Colatina", "27165729000174");
		municipioCNPJ.put("Conceição da Barra", "27174077000134");
		municipioCNPJ.put("Conceição do Castelo", "27165570000198");
		municipioCNPJ.put("Divino de São Lourenço", "27174127000183");
		municipioCNPJ.put("Domingos Martins", "27150556000110");
		municipioCNPJ.put("Dores do Rio Preto", "27167386000187");
		municipioCNPJ.put("Ecoporanga", "27167311000104");
		municipioCNPJ.put("Fundão", "27165182000107");
		municipioCNPJ.put("Governador Lindenberg", "4217786000154");
		municipioCNPJ.put("Guaçuí", "27174135000120");
		municipioCNPJ.put("Guarapari", "27165190000153");
		municipioCNPJ.put("Ibatiba", "27744150000166");
		municipioCNPJ.put("Ibiraçu", "27165208000117");
		municipioCNPJ.put("Ibitirama", "31726490000131");
		municipioCNPJ.put("Iconha", "27165646000185");
		municipioCNPJ.put("Irupi", "36403954000192");
		municipioCNPJ.put("Itaguaçu", "27167451000174");
		municipioCNPJ.put("Itapemirim", "27174168000170");
		municipioCNPJ.put("Itarana", "27104363000123");
		municipioCNPJ.put("Iúna", "27167394000123");
		municipioCNPJ.put("Jaguaré", "27744184000150");
		municipioCNPJ.put("Jerônimo Monteiro", "27165653000187");
		municipioCNPJ.put("João Neiva", "31776479000186");
		municipioCNPJ.put("Laranja da Terra", "31796097000114");
		municipioCNPJ.put("Linhares", "27167410000188");
		municipioCNPJ.put("Mantenópolis", "27167345000190");
		municipioCNPJ.put("Marataízes", "1609408000128");
		municipioCNPJ.put("Marechal Floriano", "39385927000122");
		municipioCNPJ.put("Marilândia", "27744176000104");
		municipioCNPJ.put("Mimoso do Sul", "27174119000137");
		municipioCNPJ.put("Montanha", "27174051000196");
		municipioCNPJ.put("Mucurici", "27174069000198");
		municipioCNPJ.put("Muniz Freire", "27165687000171");
		municipioCNPJ.put("Muqui", "27082403000183");
		municipioCNPJ.put("Nova Venécia", "27167428000180");
		municipioCNPJ.put("Pancas", "27174150000178");
		municipioCNPJ.put("Pedro Canário", "28539872000141");
		municipioCNPJ.put("Pinheiros", "27174085000180");
		municipioCNPJ.put("Piúma", "27165695000118");
		municipioCNPJ.put("Ponto Belo", "1614334000118");
		municipioCNPJ.put("Presidente Kennedy", "27165703000126");
		municipioCNPJ.put("Rio Bananal", "27744143000164");
		municipioCNPJ.put("Rio Novo do Sul", "27165711000172");
		municipioCNPJ.put("Santa Leopoldina", "27165521000155");
		municipioCNPJ.put("Santa Maria de Jetibá", "36388445000138");
		municipioCNPJ.put("Santa Teresa", "27167444000172");
		municipioCNPJ.put("São Domingos do Norte", "36350312000172");
		municipioCNPJ.put("São Gabriel da Palha", "27174143000176");
		municipioCNPJ.put("São José do Calçado", "27167402000131");
		municipioCNPJ.put("São Mateus", "27167477000112");
		municipioCNPJ.put("São Roque do Canaã", "1612865000171");
		municipioCNPJ.put("Serra", "27174093000127");
		municipioCNPJ.put("Sooretama", "1612155000141");
		municipioCNPJ.put("Vargem Alta", "31723570000133");
		municipioCNPJ.put("Venda Nova do Imigrante", "31723497000108");
		municipioCNPJ.put("Viana", "27165547000101");
		municipioCNPJ.put("Vila Pavão", "36350346000167");
		municipioCNPJ.put("Vila Valério", "1619232000195");
		municipioCNPJ.put("Vila Velha", "27165554000103");
		municipioCNPJ.put("Vitória", "27142058000126");
		
		// Cria o model utilizando o Jena
		Model model = ModelFactory.createDefaultModel();
				
		// Namespaces
		String myNS = "http://localhost:8080/Transparencia/dados/municipios";
		String owlNS = "http://www.w3.org/2002/07/owl#";
		model.setNsPrefix("owl", owlNS);
		
		// Gera o RDF com as informações dos municípios
		// Exemplo: Município de Vitória
		//	<rdf:Description rdf:about="http://localhost:8080/Transparencia/dados/municipios/27142058000126">
		//		<owl:sameAs rdf:resource="http://dbpedia.org/resource/Vitória"/>
		//		<rdfs:label>Vitória</rdfs:label>
		//		<rdfs:isDefinedBy rdf:resource="http://localhost:8080/Transparencia/dados/municipios?cnpj=27142058000126&municipio=Vitória"/>
		//	</rdf:Description>
	    for(String key: municipioCNPJ.keySet())
	    {	
	    	// Cria resource
			Resource rfdsIsDefinedBy = 
					ResourceFactory.createResource(myNS + "?cnpj=" + municipioCNPJ.get(key) + "&municipio=" + key);
			Resource owlSameAs = 
					ResourceFactory.createResource("http://dbpedia.org/resource/" + key.replace(" ", "_"));
	    	
			model.createResource(myNS + "/" + 	municipioCNPJ.get(key))
				.addProperty(RDFS.isDefinedBy, rfdsIsDefinedBy )
				.addProperty(RDFS.label, key)
				.addProperty(OWL.sameAs, owlSameAs); 
	    }
		
		return model;		
	}	
}