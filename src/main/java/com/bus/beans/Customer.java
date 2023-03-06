package com.bus.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;


import javax.persistence.*;

@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;

	private String cin;

	private String prenom;

	private String phone;

	private String adresse;

	private String role;

	@ManyToMany(mappedBy = "customers")
	private List<Trajet> trajets;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<TrajetAbonnement> abonnements = new ArrayList<>();

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password;
	}
	

		
	
}
