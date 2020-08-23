package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.dao.Repositorytel;
import com.example.demo.model.Client2;

@RestController
public class Controller {
	@Autowired
	Repositorytel repository;

	@Autowired
	FileConnection FileConnection;

	@GetMapping("/")
	public ModelAndView getList(@RequestParam(required = false) Integer id, HttpServletRequest request)
			throws Exception {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 1) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			int i = 0;
			for (Cookie cookie : cookies) {
			
				if (cookie.getName().equals("fileName")) {
					FileConnection.setFileName(cookie.getValue());
					i = i + 1;
				} else if (cookie.getName().equals("password")) {
					i = i + 1;
					FileConnection.setPassWord(cookie.getValue());

				}

				else if (cookie.getName().equals("id")) {
					 list.add( Integer.parseInt(cookie.getValue()));
					i = i + 1;
				}

				else if (cookie.getName().equals("name")) {
					 list.add( Integer.parseInt(cookie.getValue()));
					i = i + 1;
				} else if (cookie.getName().substring(0, cookie.getName().length() - 1).equals("cookie")) {

					   list.add( Integer.parseInt(cookie.getValue()));
				}

			}
			if (i == 4) {
				repository.setEntete(FileConnection);
				
				FileConnection.setList(list);
			} else {
				RedirectView redirectView = new RedirectView();
				redirectView.setUrl("/config");
				return new ModelAndView(redirectView);

			}
		} else {
			RedirectView redirectView = new RedirectView();
			redirectView.setUrl("/config");
			return new ModelAndView(redirectView);

		}

		Map<String, Object> model = new HashMap<String, Object>();

		if (id != null) {
			repository.delete(id, this.FileConnection);
			RedirectView redirectView = new RedirectView();
			redirectView.setUrl("/");
			return new ModelAndView(redirectView);
		}
		// System.out.println(repository.getNbclient());
		repository.setTable(this.FileConnection);
		// repository.getTable(FileConnection);
		// List<Client2> ls = repository.getTable();
		model.put("title", repository.getEntete());
		// ls.remove(0);
		model.put("list", repository.getTable());
		// System.out.println(repository.getNbclient());

		model.put("length", repository.getTable().size());

		return new ModelAndView("index", model);
		// return model;

	}

	@GetMapping("recherche")
	public ModelAndView getRecherche() throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		Id id = new Id();
		model.put("listofName", repository.getList(FileConnection, false));
		System.out.println(repository.getTable());
		// model.put("listofid",repository.getList(FileConnection, true));
		model.put("entete", repository.getEntete());
		model.put("client", id);
		model.put("list", repository.getTable());
		// model.put("title",repository.getTable().get(0));
		return new ModelAndView("find", model);
	}

	@PostMapping("recherche")
	public ModelAndView postRecherche(Id id) throws Exception {
		System.out.println(id);
		repository.setTable(id, this.FileConnection);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("recherche");
		return new ModelAndView(redirectView);
	}

	@GetMapping("config")
	public ModelAndView configuration(HttpServletRequest request, HttpServletResponse response) {
		if (FileConnection.getList() != null) {
			deconnecter(response);
		}
		Map<String, FileConnection> model = new HashMap<String, FileConnection>();
		model.put("name", new FileConnection());
		return new ModelAndView("pp", model);

	}

	@PostMapping("config")
	public ModelAndView postConfig(FileConnection fileConnection) throws Exception {

		this.FileConnection = fileConnection;
		this.FileConnection.setFileName( fileConnection.getFileName());
		System.out.println(this.FileConnection.getFileName());
		repository.setEntete(fileConnection);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("/configStep2");
		return new ModelAndView(redirectView);
	}

	@GetMapping("configStep2")
	public ModelAndView configurationStep2() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("entete", repository.getEntete());
		model.put("name", new IdMapper());
		return new ModelAndView("configS2", model);

	}

	@PostMapping("configStep2")
	public ModelAndView PostconfigurationStep2(IdMapper id, HttpServletResponse response) {
		this.FileConnection.setChamp1(repository.getEntete().indexOf(id.getId()));
		this.FileConnection.setChamp2(repository.getEntete().indexOf(id.getName()));

		RedirectView redirectView = new RedirectView();
		// String coolies = FileConnection.toString();
		Cookie cookieFile = new Cookie("fileName", FileConnection.getFileName());
		Cookie cookiePassword = new Cookie("password", FileConnection.getPassWord());
		Cookie cookieid = new Cookie("id", FileConnection.getChamp1().toString());
		Cookie cookieName = new Cookie("name", FileConnection.getChamp2().toString());
		response.addCookie(cookieName);

		response.addCookie(cookieFile);

		response.addCookie(cookieid);

		response.addCookie(cookiePassword);

		redirectView.setUrl("/configStep3");

		return new ModelAndView(redirectView);

	}

	@GetMapping("configStep3")
	public ModelAndView configStep3() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("entete", repository.getEntete());
		// System.out.println(repository.getEntete());
		model.put("name", new IdMapper());
		return new ModelAndView("configS3", model);

	}

	@PostMapping("configStep3")
	public ModelAndView configPost(IdMapper id, HttpServletResponse response) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < id.getList().size(); i++) {
			String string = "cookie" + i;
			Integer string1 = repository.getEntete().indexOf(id.getList().get(i));
			list.add(string1);
			response.addCookie(new Cookie(string, string1.toString()));
		}


		FileConnection.setList(list);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("/recherche");
		return new ModelAndView(redirectView);

	}

	@GetMapping("/formClient")
	public ModelAndView getFormClient(@RequestParam(required = false) Integer id) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", repository.getEntete());
		ClientMapping clientMapping = new ClientMapping();
		ClientMapping.setPppp(repository.getEntete().size());
		if (id == null) {
			clientMapping.setUpdate("add");
			clientMapping.setId(repository.ClientNumber() + 1);
		} else {
			clientMapping.setStringlist(repository.recupererParId(id, FileConnection).getAttributesList());
			clientMapping.setUpdate("update");
			clientMapping.setId(id);
			// System.out.println(id);

		}
		model.put("newClient", clientMapping);
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < repository.getTable().get(0).size(); i++) {
			list.add(i);
		}
		model.put("list", list);
		return new ModelAndView("clientform", model);
	}

	@PostMapping("/formClient")
	public ModelAndView postFormClient(ClientMapping string) throws Exception {
		Client2 client = new Client2();
		// System.out.println(string);

		if (string.getUpdateOrAdd().equals("update")) {
			client.setAttributesList(string.getlist());
			client.setNb(string.getId());
			repository.update(client, this.FileConnection);
		} else {
			client.setNb(string.getId());
			client.setAttributesList(string.getlist());
			repository.addClient(client, this.FileConnection);

		}

		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("/");
		return new ModelAndView(redirectView);

	}

	@GetMapping("/recupererPar")
	public ModelAndView recupererParId(@RequestParam(required = false) Integer id,
			@RequestParam(required = false) String path) throws Exception {
		if (path != null) {
			openFile(path);
			RedirectView redirectView = new RedirectView();

			redirectView.setUrl("/recupererPar/?id=" + id);
			return new ModelAndView(redirectView);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		// System.out.println(repository.recupererParId(id,FileConnection).Other(FileConnection));
		model.put("client1", repository.recupererParId(id, FileConnection).linklist(FileConnection));
		model.put("title1", repository.EnteteLink(FileConnection));

		model.put("client0", repository.recupererParId(id, FileConnection).Other(FileConnection));
		model.put("title0", repository.EntetOther(FileConnection));
		model.put("idClient", repository.recupererParId(id, FileConnection).getNb());

		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list0 = new ArrayList<Integer>();
		for (int i = 0; i < repository.EntetOther(FileConnection).size(); i++) {
			list1.add(i);
		}
		model.put("list0", list1);
		for (int i = 0; i < repository.EnteteLink(FileConnection).size(); i++) {
			list0.add(i);
		}
		model.put("list1", list0);
		return new ModelAndView("page2", model);

	}

	private void openFile(String path) {
		try {

			// System.out.println("File is not exists");
			String pp = "rundll32 url.dll,FileProtocolHandler " + path;
			Process p = Runtime.getRuntime().exec(pp);
			p.waitFor();

			System.out.println("Done");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void deconnecter(HttpServletResponse response) {
		for (int i = 0; i < FileConnection.getList().size(); i++) {
			String string = "cookie" + i;
			Cookie cookie = new Cookie(string, null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);

		}
		Cookie cookieFile = new Cookie("fileName", null);
		cookieFile.setMaxAge(0);
		Cookie cookiePassword = new Cookie("password", null);
		cookiePassword.setMaxAge(0);
		Cookie cookieid = new Cookie("id", null);
		cookieid.setMaxAge(0);
		Cookie cookieName = new Cookie("name", null);
		cookieName.setMaxAge(0);
		response.addCookie(cookieName);
		response.addCookie(cookieFile);
		response.addCookie(cookieid);
		response.addCookie(cookiePassword);

	}

}
