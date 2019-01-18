package com.ibm.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.demo.model.Message;

@RestController
@RequestMapping( value = "messages", produces = "application/hal+json" )
public class HATEOASController {
	private static final List<Message> messages = new ArrayList<Message>();

	static {
		messages.add( new Message( "msg1" ) );
		messages.add( new Message( "msg2" ) );

	}

	@GetMapping( "/" )
	public Resources<Resource<Message>> index() {
		List<Resource<Message>> reslt = IntStream.range( 0, messages.size() ).mapToObj( idx -> new Resource<>( messages.get( idx ), link( String.valueOf( idx + 1 ) ) ) ).collect( Collectors.toList() );

		return new Resources<>( reslt, ControllerLinkBuilder.linkTo( HATEOASController.class ).withSelfRel() );
	}

	@GetMapping( "/{id}" )
	public Resource<Message> show( @PathVariable( "id" ) String id ) {
		return new Resource<>( messages.get( Integer.parseInt( id ) - 1 ), link( id ) );
	}

	@PostMapping( "/" )
	public Resource<Message> create( @RequestBody Message message ) {
		messages.add( message );
		return new Resource<>( message );
	}

	@DeleteMapping( "/{id}" )
	public Resource<Message> delete( @PathVariable( "id" ) String id ) {
		return new Resource<>( messages.remove( Integer.parseInt( id ) - 1 ) );
	}

	private Link link( String id ) {
		return ControllerLinkBuilder.linkTo( HATEOASController.class ).slash( id ).withSelfRel();
	}
}