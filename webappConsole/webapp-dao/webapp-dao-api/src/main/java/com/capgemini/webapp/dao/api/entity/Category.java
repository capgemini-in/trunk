package com.capgemini.webapp.dao.api.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Category")
public class Category implements Serializable {
	
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "category_id")
		private Integer categoryId;

		@Column(name = "category_code")
		private String categoryCode;
		
		@Column(name = "category_name")
		private String categoryName;

		@Column(name = "image_Path")
		private String imagePath;
				
		@ManyToOne
		@JoinColumn(name="sub_menu_id")	
		@JsonManagedReference
		private BusinessSubMenu submenuId;
	

		public Integer getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(Integer categoryId) {
			this.categoryId = categoryId;
		}

		public String getCategoryCode() {
			return categoryCode;
		}

		public void setCategoryCode(String categoryCode) {
			this.categoryCode = categoryCode;
		}

		public String getCategoryName() {
			return categoryName;
		}

		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}

		public String getImagePath() {
			return imagePath;
		}

		public void setImagePath(String imagePath) {
			this.imagePath = imagePath;
		}

		public BusinessSubMenu getSubmenuId() {
			return submenuId;
		}

		public void setSubmenuId(BusinessSubMenu submenuId) {
			this.submenuId = submenuId;
		}
	
		

}
