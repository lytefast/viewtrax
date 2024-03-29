package viewTrax.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query.FilterOperator;

import viewTrax.SingletonWrapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


/**
 * Hold the name for the title
 * 
 * @author ShihSam@gmail.com
 */
@PersistenceCapable( identityType = IdentityType.APPLICATION )
public class TitleName {

	/**
	 * Identifier for this object
	 */
	@PrimaryKey
	@Persistent( valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Key		key;

	/**
	 * {@link Title} for which this name is associated with
	 */
	@ForeignKey
	@Persistent
	private Title	title;

	/**
	 * Name of the title we are associated with (this could be a variation of
	 * the official name such as in another language)
	 */
	@Persistent
	private String	name;

	public TitleName( Title title, String name ) {
		this.title = title;
		this.setName( name );
	}

	public Key getKey() {
		return key;
	}

	public String toString() {
		return "[" + key + "] " + getName();
	}

	public void setTitle( Title title ) {
		this.title = title;
	}

	public Title getTitle() {
		return title;
	}

	// TODO public Type language;

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName( String name ) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get a list of {@link TitleName} which have the matching title name
	 * 
	 * @param pm
	 *            PersistenceManager to use for query
	 * @param title
	 *            Name of the title
	 * @return {@link List} of {@link TitleName}s matching the title provided
	 */
	public static List<TitleName> get( PersistenceManager pm, String title ) {
		Query query = getQuery( pm );
		String filter = "name == titleName";
		query.setFilter( filter );
		query.declareParameters( "String titleName" );
		Object obj = query.execute( title );

		List<TitleName> list = (List<TitleName>) obj;
		return list;
	}

	/**
	 * Get the first {@link TitleName} that appears in a query with the title
	 * 
	 * @param pm
	 *            PersistenceManager to use for query
	 * @param title
	 *            Name of the title
	 * @return {@link TitleName} object matching the title provided
	 */
	public static TitleName getFirst( PersistenceManager pm, String title )
			throws Exception {
		List<TitleName> list = get( pm, title );
		if( !list.isEmpty() ) {
			return list.get( 0 );
		}

		// throw new Exception("Title: " + title + " not found.");
		return null;
	}

	private static Query getQuery( PersistenceManager pm ) {
		return pm.newQuery( TitleName.class );
	}
}
