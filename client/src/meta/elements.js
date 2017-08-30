export const elementMeta = (
  {
    "applicationComponent": {
      "name": "ApplicationComponent",
      "layer": "Application",
      "help": {
        "summ": ["An encapsulation of application functionality aligned to implementation structure, which is modular and replaceable. It encapsulates its behavior and data, exposes services, and makes them available through interfaces."],
        "info": ["An application component represents an encapsulation of application functionality aligned to implementation structure, which is modular and replaceable. It encapsulates its behavior and data, exposes services, and makes them available through interfaces."]
      }
    },
    "technologyService": {
      "name": "TechnologyService",
      "layer": "Technology",
      "help": {
        "summ": ["An explicitly defined exposed technology behavior."],
        "info": ["A technology service represents an explicitly defined exposed technology behavior."]
      }
    },
    "systemSoftware": {
      "name": "SystemSoftware",
      "layer": "Technology",
      "help": {
        "summ": ["Software that provides or contributes to an environment for storing, executing, and using software or data deployed within it."],
        "info": ["System software represents software that provides or contributes to an environment for storing, executing, and using software or data deployed within it."]
      }
    },
    "plateau": {
      "name": "Plateau",
      "layer": "Implementation",
      "help": {
        "summ": ["A relatively stable state of the architecture that exists during a limited period of time."],
        "info": ["A plateau represents a relatively stable state of the architecture that exists during a limited period of time."]
      }
    },
    "technologyInterface": {
      "name": "TechnologyInterface",
      "layer": "Technology",
      "help": {
        "summ": ["A point of access where technology services offered by a node can be accessed."],
        "info": ["A technology interface represents a point of access where technology services offered by a node can be accessed."]
      }
    },
    "location": {
      "name": "Location",
      "layer": "Composition",
      "help": {
        "summ": ["A place or position where structure elements can be located or behavior can be performed"],
        "info": ["A location is a place or position where structure elements can be located or behavior can be performed."]
      }
    },
    "path": {
      "name": "Path",
      "layer": "Technology",
      "help": {
        "summ": ["A link between two or more nodes, through which these nodes can exchange data or material."],
        "info": ["A path represents a link between two or more nodes, through which these nodes can exchange data or material."]
      }
    },
    "applicationEvent": {
      "name": "ApplicationEvent",
      "layer": "Application",
      "help": {
        "summ": ["An application behavior element that denotes a state change."],
        "info": ["An application event is an application behavior element that denotes a state change."]
      }
    },
    "applicationInterface": {
      "name": "ApplicationInterface",
      "layer": "Application",
      "help": {
        "summ": ["A point of access where application services are made available to a user, another application component, or a node."],
        "info": ["An application interface represents a point of access where application services are made available to a user, another application component, or a node."]
      }
    },
    "workPackage": {
      "name": "WorkPackage",
      "layer": "Implementation",
      "help": {
        "summ": ["A series of actions identified and designed to achieve specific results within specified time and resource constraints."],
        "info": ["A work package represents a series of actions identified and designed to achieve specific results within specified time and resource constraints."]
      }
    },
    "implementationEvent": {
      "name": "ImplementationEvent",
      "layer": "Implementation",
      "help": {
        "summ": ["A behavior element that denotes a state change related to implementation or migration."],
        "info": ["An implementation event is a behavior element that denotes a state change related to implementation or migration."]
      }
    },
    "businessObject": {
      "name": "BusinessObject",
      "layer": "Business",
      "help": {
        "summ": ["A concept used within a particular business domain."],
        "info": ["A business object represents a concept used within a particular business domain."]
      }
    },
    "grouping": {
      "name": "Grouping",
      "layer": "Composition",
      "help": {
        "summ": ["Aggregates or composes concepts that belong together based on some common characteristic."],
        "info": ["The grouping element aggregates or composes concepts that belong together based on some common characteristic."]
      }
    },
    "stakeholder": {
      "name": "Stakeholder",
      "layer": "Motivation",
      "help": {
        "summ": ["The role of an individual, team, or organization (or classes thereof) that represents their interests in the outcome of the architecture."],
        "info": ["A stakeholder is the role of an individual, team, or organization (or classes thereof) that represents their interests in the outcome of the architecture."]
      }
    },
    "deliverable": {
      "name": "Deliverable",
      "layer": "Implementation",
      "help": {
        "summ": ["A precisely-defined outcome of a work package."],
        "info": ["A deliverable represents a precisely-defined outcome of a work package."]
      }
    },
    "businessActor": {
      "name": "BusinessActor",
      "layer": "Business",
      "help": {
        "summ": ["A business entity that is capable of performing behavior."],
        "info": ["A business actor is a business entity that is capable of performing behavior."]
      }
    },
    "applicationInteraction": {
      "name": "ApplicationInteraction",
      "layer": "Application",
      "help": {
        "summ": ["A unit of collective application behavior performed by (a collaboration of) two or more application components."],
        "info": ["An application interaction represents a unit of collective application behavior performed by (a collaboration of) two or more application components."]
      }
    },
    "technologyProcess": {
      "name": "TechnologyProcess",
      "layer": "Technology",
      "help": {
        "summ": ["A sequence of technology behaviors that achieves a specific outcome."],
        "info": ["A technology process represents a sequence of technology behaviors that achieves a specific outcome."]
      }
    },
    "principle": {
      "name": "Principle",
      "layer": "Motivation",
      "help": {
        "summ": ["A qualitative statement of intent that should be met by the architecture."],
        "info": ["A principle represents a qualitative statement of intent that should be met by the architecture."]
      }
    },
    "outcome": {
      "name": "Outcome",
      "layer": "Motivation",
      "help": {
        "summ": ["An end result that has been achieved."],
        "info": ["An outcome represents an end result that has been achieved."]
      }
    },
    "goal": {
      "name": "Goal",
      "layer": "Motivation",
      "help": {
        "summ": ["A high-level statement of intent, direction, or desired end state for an organization and its stakeholders."],
        "info": ["A goal represents a high-level statement of intent, direction, or desired end state for an organization and its stakeholders."]
      }
    },
    "businessService": {
      "name": "BusinessService",
      "layer": "Business",
      "help": {
        "summ": ["An explicitly defined exposed business behavior."],
        "info": ["A business service represents an explicitly defined exposed business behavior."]
      }
    },
    "technologyFunction": {
      "name": "TechnologyFunction",
      "layer": "Technology",
      "help": {
        "summ": ["A collection of technology behavior that can be performed by a node."],
        "info": ["A technology function represents a collection of technology behavior that can be performed by a node."]
      }
    },
    "applicationService": {
      "name": "ApplicationService",
      "layer": "Application",
      "help": {
        "summ": ["An explicitly defined exposed application behavior."],
        "info": ["An application service represents an explicitly defined exposed application behavior."]
      }
    },
    "technologyCollaboration": {
      "name": "TechnologyCollaboration",
      "layer": "Technology",
      "help": {
        "summ": ["An aggregate of two or more nodes that work together to perform collective technology behavior."],
        "info": ["A technology collaboration represents an aggregate of two or more nodes that work together to perform collective technology behavior."]
      }
    },
    "requirement": {
      "name": "Requirement",
      "layer": "Motivation",
      "help": {
        "summ": ["A statement of need that must be met by the architecture."],
        "info": ["A requirement represents a statement of need that must be met by the architecture."]
      }
    },
    "driver": {
      "name": "Driver",
      "layer": "Motivation",
      "help": {
        "summ": ["An external or internal condition that motivates an organization to define its goals and implement the changes necessary to achieve them."],
        "info": ["A driver represents an external or internal condition that motivates an organization to define its goals and implement the changes necessary to achieve them."]
      }
    },
    "resource": {
      "name": "Resource",
      "layer": "Strategy",
      "help": {
        "summ": ["An asset owned or controlled by an individual or organization."],
        "info": ["A resource represents an asset owned or controlled by an individual or organization."]
      }
    },
    "distributionNetwork": {
      "name": "DistributionNetwork",
      "layer": "Physical",
      "help": {
        "summ": ["A physical network used to transport materials or energy."],
        "info": ["A distribution network represents a physical network used to transport materials or energy."]
      }
    },
    "applicationCollaboration": {
      "name": "ApplicationCollaboration",
      "layer": "Application",
      "help": {
        "summ": ["An aggregate of two or more application components that work together to perform collective application behavior."],
        "info": ["An application collaboration represents an aggregate of two or more application components that work together to perform collective application behavior."]
      }
    },
    "gap": {
      "name": "Gap",
      "layer": "Implementation",
      "help": {
        "summ": ["A statement of difference between two plateaus."],
        "info": ["A gap represents a statement of difference between two plateaus."]
      }
    },
    "meaning": {
      "name": "Meaning",
      "layer": "Motivation",
      "help": {
        "summ": ["The knowledge or expertise present in, or the interpretation given to, a core element in a particular context."],
        "info": ["Meaning represents the knowledge or expertise present in, or the interpretation given to, a core element in a particular context."]
      }
    },
    "technologyInteraction": {
      "name": "TechnologyInteraction",
      "layer": "Technology",
      "help": {
        "summ": ["A unit of collective technology behavior performed by (a collaboration of) two or more nodes."],
        "info": ["A technology interaction represents a unit of collective technology behavior performed by (a collaboration of) two or more nodes."]
      }
    },
    "applicationProcess": {
      "name": "ApplicationProcess",
      "layer": "Application",
      "help": {
        "summ": ["A sequence of application behaviors that achieves a specific outcome."],
        "info": ["An application process represents a sequence of application behaviors that achieves a specific outcome."]
      }
    },
    "dataObject": {
      "name": "DataObject",
      "layer": "Application",
      "help": {
        "summ": ["Data structured for automated processing."],
        "info": ["A data object represents data structured for automated processing."]
      }
    },
    "businessFunction": {
      "name": "BusinessFunction",
      "layer": "Business",
      "help": {
        "summ": ["A collection of business behavior based on a chosen set of criteria (typically required business resources and/or competencies), closely aligned to an organization, but not necessarily explicitly governed by the organization."],
        "info": ["A business function is a collection of business behavior based on a chosen set of criteria (typically required business resources and/or competences), closely aligned to an organization, but not necessarily explicitly governed by the organization."]
      }
    },
    "capability": {
      "name": "Capability",
      "layer": "Strategy",
      "help": {
        "summ": ["An ability that an active structure element, such as an organization, person, or system, possesses."],
        "info": ["A capability represents an ability that an active structure element, such as an organization, person, or system, possesses."]
      }
    },
    "assessment": {
      "name": "Assessment",
      "layer": "Motivation",
      "help": {
        "summ": ["The result of an analysis of the state of affairs of the enterprise with respect to some driver."],
        "info": ["An assessment represents the result of an analysis of the state of affairs of the enterprise with respect to some driver."]
      }
    },
    "constraint": {
      "name": "Constraint",
      "layer": "Motivation",
      "help": {
        "summ": ["A factor that prevents or obstructs the realization of goals."],
        "info": ["A constraint represents a factor that prevents or obstructs the realization of goals."]
      }
    },
    "material": {
      "name": "Material",
      "layer": "Physical",
      "help": {
        "summ": ["Tangible physical matter or physical elements."],
        "info": ["Material represents tangible physical matter or physical elements."]
      }
    },
    "businessProcess": {
      "name": "BusinessProcess",
      "layer": "Business",
      "help": {
        "summ": ["A sequence of business behaviors that achieves a specific outcome such as a defined set of products or business services."],
        "info": ["A business process represents a sequence of business behaviors that achieves a specific outcome such as a defined set of products or business services."]
      }
    },
    "equipment": {
      "name": "Equipment",
      "layer": "Physical",
      "help": {
        "summ": ["One or more physical machines, tools, or instruments that can create, use, store, move, or transform materials."],
        "info": ["Equipment represents one or more physical machines, tools, or instruments that can create, use, store, move, or transform materials."]
      }
    },
    "businessInteraction": {
      "name": "BusinessInteraction",
      "layer": "Business",
      "help": {
        "summ": ["A unit of collective business behavior performed by (a collaboration of) two or more business roles."],
        "info": ["A business interaction is a unit of collective business behavior performed by (a collaboration of) two or more business roles."]
      }
    },
    "node": {
      "name": "Node",
      "layer": "Technology",
      "help": {
        "summ": ["A computational or physical resource that hosts, manipulates, or interacts with other computational or physical resources."],
        "info": ["A node represents a computational or physical resource that hosts, manipulates, or interacts with other computational or physical resources."]
      }
    },
    "applicationFunction": {
      "name": "ApplicationFunction",
      "layer": "Application",
      "help": {
        "summ": ["Automated behavior that can be performed by an application component."],
        "info": ["An application function represents automated behavior that can be performed by an application component."]
      }
    },
    "technologyEvent": {
      "name": "TechnologyEvent",
      "layer": "Technology",
      "help": {
        "summ": ["A technology behavior element that denotes a state change."],
        "info": ["A technology event is a technology behavior element that denotes a state change."]
      }
    },
    "businessRole": {
      "name": "BusinessRole",
      "layer": "Business",
      "help": {
        "summ": ["The responsibility for performing specific behavior, to which an actor can be assigned, or the part an actor plays in a particular action or event."],
        "info": ["A business role is the responsibility for performing specific behavior, to which an actor can be assigned, or the part an actor plays in a particular action or event."]
      }
    },
    "businessEvent": {
      "name": "BusinessEvent",
      "layer": "Business",
      "help": {
        "summ": ["A business behavior element that denotes an organizational state change. It may originate from and be resolved inside or outside the organization."],
        "info": ["A business event is a business behavior element that denotes an organizational state change. It may originate from and be resolved inside or outside the organization."]
      }
    },
    "facility": {
      "name": "Facility",
      "layer": "Physical",
      "help": {
        "summ": ["A physical structure or environment."],
        "info": ["A facility represents a physical structure or environment."]
      }
    },
    "businessCollaboration": {
      "name": "BusinessCollaboration",
      "layer": "Business",
      "help": {
        "summ": ["An aggregate of two or more business internal active structure elements that work together to perform collective behavior."],
        "info": ["A business collaboration is an aggregate of two or more business internal active structure elements that work together to perform collective behavior."]
      }
    },
    "courseOfAction": {
      "name": "CourseOfAction",
      "layer": "Strategy",
      "help": {
        "summ": ["An approach or plan for configuring some capabilities and resources of the enterprise, undertaken to achieve a goal."],
        "info": ["A course of action is an approach or plan for configuring some capabilities and resources of the enterprise, undertaken to achieve a goal."]
      }
    },
    "value": {
      "name": "Value",
      "layer": "Motivation",
      "help": {
        "summ": ["The relative worth, utility, or importance of a core element or an outcome."],
        "info": ["Value represents the relative worth, utility, or importance of a core element or an outcome."]
      }
    },
    "representation": {
      "name": "Representation",
      "layer": "Business",
      "help": {
        "summ": ["A perceptible form of the information carried by a business object."],
        "info": ["A representation represents a perceptible form of the information carried by a business object."]
      }
    },
    "communicationNetwork": {
      "name": "CommunicationNetwork",
      "layer": "Technology",
      "help": {
        "summ": ["A set of structures that connects computer systems or other electronic devices for transmission, routing, and reception of data or data-based communications such as voice and video."],
        "info": ["A communication network represents a set of structures and behaviors that connects computer systems or other electronic devices for transmission, routing, and reception of data or data-based communications such as voice and video."]
      }
    },
    "device": {
      "name": "Device",
      "layer": "Technology",
      "help": {
        "summ": ["A physical IT resource upon which system software and artifacts may be stored or deployed for execution."],
        "info": ["A device is a physical IT resource upon which system software and artifacts may be stored or deployed for execution."]
      }
    },
    "contract": {
      "name": "Contract",
      "layer": "Business",
      "help": {
        "summ": ["A formal or informal specification of an agreement between a provider and a consumer that specifies the rights and obligations associated with a product and establishes functional and non-functional parameters for interaction."],
        "info": ["A contract represents a formal or informal specification of an agreement between a provider and a consumer that specifies the rights and obligations associated with a product and establishes functional and non-functional parameters for interaction."]
      }
    },
    "artifact": {
      "name": "Artifact",
      "layer": "Technology",
      "help": {
        "summ": ["A piece of data that is used or produced in a software development process, or by deployment and operation of a system."],
        "info": ["An artifact represents a piece of data that is used or produced in a software development process, or by deployment and operation of an IT system."]
      }
    },
    "businessInterface": {
      "name": "BusinessInterface",
      "layer": "Business",
      "help": {
        "summ": ["A point of access where a business service is made available to the environment."],
        "info": ["A business interface is a point of access where a business service is made available to the environment."]
      }
    },
    "product": {
      "name": "Product",
      "layer": "Business",
      "help": {
        "summ": ["A coherent collection of services and/or passive structure elements, accompanied by a contract/set of agreements, which is offered as a whole to (internal or external) customers."],
        "info": ["A product represents a coherent collection of services and/or passive structure elements, accompanied by a contract/set of agreements, which is offered as a whole to (internal or external) customers."]
      }
    }
  }
);
