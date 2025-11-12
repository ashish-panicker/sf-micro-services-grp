# REST vs gRPC — Overview

## REST API
A REST (Representational State Transfer) API is an architectural style that uses standard HTTP methods (GET, POST, PUT, DELETE) and typically JSON for request/response payloads. It is resource-oriented and widely used for web services and public APIs.

Works on a Request Response Model

### Common use cases
- Public web APIs consumed by browsers and third-party developers
- CRUD-based web applications and microservices
- Simple integrations where human readability (JSON) is helpful
- When caching, proxies, and HTTP semantics are required

### Advantages
- Wide adoption and broad tooling/hosting support
- Human-readable JSON makes debugging and manual testing easy
- Works well with browsers and standard HTTP infrastructure (caching, proxies, auth)
- Language-agnostic and simple to document (OpenAPI/Swagger)

### Disadvantages
- JSON is verbose and slower to parse compared to binary formats
- No built-in streaming or multiplexing over a single connection (HTTP/1.1)
- Potentially higher latency and bandwidth for high-throughput services
- Less strict contract enforcement (schema validation is optional)

## gRPC
gRPC is a high-performance RPC framework that uses HTTP/2 as a transport, Protocol Buffers (protobuf) for binary serialization, and generates typed client/server stubs. It supports unary RPCs, server/client streaming, and bidirectional streaming.

Uses protobuf which serializes data reducing the overhead of the app to serialize and deserialize data.

Supports multiple mode of comunications:
- Unary
- Server Streaming (Server sends multiple responds)
- Client Streaming (Client sends mutiple requests with server responding once)
- Bi Directional Streaming

### Common use cases
- High-performance internal microservices communication
- Real-time streaming services (metrics, telemetry, chat)
- Polyglot environments where strong typing and code generation reduce boilerplate
- Low-latency, high-throughput backend-to-backend communication

### Advantages
- Efficient binary serialization (smaller payloads, faster parsing)
- HTTP/2 features: multiplexing, header compression, single-connection concurrency
- First-class support for streaming and bidirectional communication
- Strongly-typed contracts with auto-generated client/server code (protobuf)
- Better performance for high-throughput and low-latency scenarios

### Disadvantages
- Less friendly for direct browser consumption (requires gRPC-Web or proxies)
- Steeper learning curve (protobufs, IDLs, tooling)
- Less human-readable payloads (binary protobuf)
- Requires language/tooling support for efficient developer experience
- Debugging raw messages needs additional tooling compared to JSON

## When to choose which
- Choose REST when you need broad compatibility, human-readable payloads, easy consumption from browsers, and simple public APIs.
- Choose gRPC for internal services requiring high performance, streaming, strong typing, and efficient binary protocols.

## Protobuf

