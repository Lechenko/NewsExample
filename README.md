📦 NewsExample — Modular Architecture with Ports & UseCase-driven Domain

This project is not just a "news app".
It is an architecture example demonstrating a scalable, modular, and decoupled system built using a port-oriented (hexagonal-like) approach.

🧠 Idea
The main idea of this project is:
Plain text
Separate contracts from implementation
Build system around use cases, not UI
Keep domain independent from frameworks and data sources
Make modules replaceable and scalable

🏗 Architecture Overview
The project is split into independent modules:
Plain text
presentation
domain
data
ports (portPresentation / portDomain / portData)
features (api / dao / security)
dependency (DI)
test (separate consumer module)

🔌 Ports (Core Concept)
Instead of directly coupling layers, all communication goes through ports (interfaces):
Plain text
PortPresentation → contract for UI interaction
PortDomain → contract for business logic
PortData → contract for data sources
This allows:
replacing implementations without breaking layers
isolating dependencies
supporting multiple data sources or SDKs

🧠 Domain Layer (Not Just Models)
The domain layer contains:
UseCases (Interactors)
Business logic
State handling
Example:
Plain text
NewsUseCase
FavoritesUseCase
MainUseCase
Domain communicates only through:
Plain text
PortData (repositories)
and returns state via reactive streams (Rx).

🔄 Data Layer
Data layer is split into independent feature modules:
Plain text
featureRemoteApi
featureLocalStorage
featureSecurity
Each module implements PortData contracts.
This allows combining different data sources:
Plain text
API + Local DB + Cache + Secure storage

🎯 Presentation Layer
Presentation is also modular:
Plain text
FeatureView
XmlRes
ComposeRes
UI is separated from logic and communicates only via PortPresentation.

🧪 Test Module (Important)
Tests are implemented as a separate module, not inside features.
Plain text
test module = independent consumer of architecture
This allows testing:
full data → domain → presentation flow
real integration between modules
without tight coupling
🔁 Flow Example
Plain text
UI → PortPresentation → Domain (UseCase)
    → PortData → Data (API/DB)
    → Domain maps result → returns state
    → UI observes state
💡 Why this approach
This architecture was designed to support:
scalable applications
complex flows (like fintech / SDK / device integration)
multi-source data systems
replaceable implementations (including hardware/SDK adapters)
⚠️ Note
This project is an architecture demonstration, not a production-ready news app.
The focus is on:
Plain text
structure > UI
contracts > implementations
scalability > simplicity
🚀 Summary
Plain text
This project demonstrates:
✔ Modular architecture
✔ Port-based communication
✔ UseCase-driven domain
✔ Independent test module
✔ Scalable structure for complex systems
